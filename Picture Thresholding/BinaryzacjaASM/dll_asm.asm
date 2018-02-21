.data
; Those 9 arrays determinate elements of which indexes in one array should be change to others. 
; -1 means that index will not be changed. Different values are indexes in the array the values will be copied from.
; Those arrays are needed to unpack RGB values in such way, that in one array will be only on color values.
R_First db 0, -1, 3, -1, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
R_Second db -1, -1, -1, -1, -1, -1, 1, -1, 4, -1, 7, -1, -1, -1, -1, -1
R_Third db -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, -1, 5, -1

G_First db 1, -1, 4, -1, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
G_Second db -1, -1, -1, -1, -1, -1, 2, -1, 5, -1, -1, -1, -1, -1, -1, -1
G_Third db -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, 3, -1, 6, -1

B_First db 2, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
B_Second db -1, -1, -1, -1, 0, -1, 3, -1, 6, -1, -1, -1, -1, -1, -1, -1
B_Third db -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, 4, -1, 7, -1

; Those three arrays determiante which elements in one array will be cahnged into elements from another array. Used just as arrays above.
; They are need to pack elements to array that is sent to the function.
FirstEightEl db 0, 0, 0, 2, 2, 2, 4, 4, -1, -1, -1, -1, -1, -1, -1, -1
SecondEightEl db 4, 6, 6, 6, 8, 8, 8, 10, -1, -1, -1, -1, -1, -1, -1, -1
ThirdEightEl db 10, 10, 12, 12, 12, 14, 14, 14,-1, -1, -1, -1, -1, -1, -1, -1

.CODE

; Procedure counting bitmap array element's sum without using SSE instructions.
; Passed parameters:
; rcx - array of bytes representing bitmap pixels (one pixel = three elements in this array)
; rdx - begining; first element of this array we want to count
; r8 - end; the element just after the last element of the array we want to count
CountSumASM proc 
	mov rsi, rdx								;setting index to begining of the range
	xor rax,rax									;resetting an accumulator
	xor rbx,rbx									;resetting the register in which the value of current element will be stored
	next:
		mov bl, byte ptr[rcx+rsi]				;moving one element from byte's array (which means one color of one pixel)
		add rax, rbx							;adding value of those element to sum of elements
		inc rsi									;counter incrementation
		cmp r8, rsi								;chcecking if counter is higher or lower than the end of the range
		jnz next								;if counter is lower, this loop is supposed to be exectued again

	ret											;the content of the accumulator is returned; it contains the sum of array elements
CountSumASM endp

; Procedure editing bitmap array's element (setting every one of them either into 0 or 255) without using SSE instructions
; Passed parameters:
; rcx - array of bytes representing bitmap pixels (one pixel = three elements in this array)
; rdx - begining; first element of this array we want to edit
; r8 - end; the element just after the last element of the array we want to edit
; r9  - global average of all elements in the array
EditArrayASM proc
	mov rsi, rdx								;setting index to begining of the range
	xor rbx,rbx									;resetting register in which the local sum will be stored
	imul r9, 3									;multiplying global sum by three, so that we will not have to divide sum of three elements

	;counting local sum of three elements
	next:
		xor rax,rax								;resetting an accumulator (in which the local sum will be stored)
		mov bl, byte ptr[rcx+rsi]				;moving one element from byte's array (which means one color of one pixel)
		add rax,rbx								;adding it local sum
		inc rsi									;incrementing counter
		mov bl, byte ptr[rcx+rsi]				;this way we add next two elements
		add rax,rbx
		inc rsi
		mov bl, byte ptr[rcx+rsi]
		add rax,rbx

		sub rsi, 2								;when we will be setting array element's values into new ones, we will have to start from the one we began counting local average with
		cmp rax, r9								;comparing local sum to the global one
		jb  black								;if not below - change pixel to white
		jmp white								;otherwise to black

		white: 
			mov byte ptr[rcx+rsi],255			;replacing three elements with 255 value
			inc rsi
			mov byte ptr[rcx+rsi],255
			inc rsi
			mov byte ptr[rcx+rsi],255
			inc rsi
			cmp r8, rsi							;chcecking if counter is higher or lower than the end of the range
			jnb next							;if counter is lower, this loop is supposed to be exectued again
			jmp endd							;otherwise we shoudl end executing an algorithm

		black: 
			mov byte ptr[rcx+rsi],0				;doing the same as in "white" case
			inc rsi
			mov byte ptr[rcx+rsi],0
			inc rsi
			mov byte ptr[rcx+rsi],0
			inc rsi
			cmp r8, rsi 
			jnb next 
			jmp endd

endd: ret
EditArrayASM endp

; Procedure counting bitmap array element's sum using SSE instructions.
; Passed parameters:
; rcx - array of bytes representing bitmap pixels (one pixel = three elements in this array)
; rdx - begining; first element of this array we want to count
; r8 - end; the element just after the last element of the array we want to count
CountSumASMSIMD proc

	xor rax,rax									;resetting accumulator; it will store the entire sum
	xor rbx,rbx									;resetting accumulator in which temporarly will be stored sum after signle loop execution
	xor rdi, rdi								;counter used to check if the sum of elements could overrun the memory it can use (2B)
	mov rsi, rdx								;setting index to begining of the range
	sub r8, 8									;this must be done so we will never step into the loop in which we might try to acces array elements that don't exist (in each iteration we read 8 elements)

	pxor xmm3,xmm3								;register storing sum of signle loop execution
	pxor xmm4,xmm4								;temporary register needed to add all sums in single xmm register into another register
	pxor xmm5,xmm5								;this register will store the entire sum from this procedure
	
	next:
		pxor xmm1,xmm1							;the register in which all bits are set to 0 is needed in each iteration to execute punpcklbw command
		movq xmm0, qword ptr [rcx+rsi]			;first eight elements from bitmap array will be stored in xmm0 register (in lower half so far)
		punpcklbw xmm0, xmm1					;the xmm0 register content will be "extended" - there will be two bytes instead of one to use for one element
		paddusw xmm3, xmm0;						;adding eight elements to current sum
		add rsi, 8								;in next iteration we will be adding next eight elements
		inc rdi									;incrementing counter checking if the sum may be too large
		cmp r8,rsi								;checking if the end of range isn't too close
		jb copySum								;if we reached the end, we must copy counted in this loop sum
		cmp rdi,257								;after 257 iterations we may reach the max size of memory which is selected for sum in xmm register so we must copy the sum to the global sum and reset this register
		jne next
		jmp copySum

	copySum:
		pxor xmm6,xmm6							;resetting temporary registers
		pxor xmm7,xmm7
		movq xmm7, xmm3							;bytes from lower half or register which cotains sum from loop we executed before is moved to xmm7; it will be handled later
		punpckhwd xmm3,xmm6						;"extends" upper half of register containing local sum - each element takes 32 bits
		phaddd xmm3,xmm3						;adding pairs of elements - each sum takes 64 bits
		movq xmm4, xmm3							;getting rid of upper half (it contaisn same number as the lower one)
		phaddd xmm4,xmm4						;adding pairs of elements again
		movq xmm3, xmm4							;moving sum into lower half
		paddq xmm5,xmm3							;adding this sum to the global sum counted within entire procedure

		pxor xmm3, xmm3							;repeated for the lower half 
		punpcklwd xmm7, xmm3
		phaddd xmm7,xmm7
		movq xmm4, xmm7
		phaddd xmm4,xmm4
		movq xmm7, xmm4
		paddq xmm5,xmm7							;now in xmm5 is sum of the entire array
		xor rdi, rdi							;in case of jumping into "next" loop
		cmp r8,rsi								;if there are at least eight left elements, the loop "next" must be executed again
		jnb next

	vmovq rax, xmm5								;adding current sum to the global one
	add r8,8									;back to actual end of the range
	cmp r8, rsi									;checking if there are any left elements so add to sum
	je endSum									;if there are no left elements, we must jump into the end of the procedure
	jnb addRest									;if there are such elements, we must add them as well but without using SSE operations

	addRest:
		mov bl, byte ptr[rcx+rsi]				;moving one element from byte's array (which means one color of one pixel)
		add rax,rbx								;adding it entire sum
		inc rsi									;incrementing counter
		cmp r8,rsi								;chcecking if counter is higher or lower than the end of the range
		je endSum
		jmp addRest 							;if counter is lower, this loop is supposed to be exectued again

	endSum: ret									;the content of the accumulator is returned; it contains the sum of array elements
CountSumASMSIMD endp

; Procedure editing bitmap array's element (setting every one of them either into 0 or 255) using SSE instructions
; Passed parameters:
; rcx - array of bytes representing bitmap pixels (one pixel = three elements in this array)
; rdx - begining; first element of this array we want to edit
; r8 - end; the element just after the last element of the array we want to edit
; r9  - global average of all elements in the array
EditArrayASMSIMD proc

	imul r9, 3									;multiplying global sum by three, so that we will not have to divide sum of three elements
	mov rsi, rdx								;setting index to begining of the range
	sub r8, 24									;this must be done so we will never step into the loop in which we might try to acces array elements that don't exist (in each iteration we read 24 elements)

	vmovq xmm15, r9;							;moving global average multiplied by three to xmm15 register
	punpcklwd xmm15, xmm15						;those three lines "extends" xmm15 register so that it's content will be repeated every two bytes
	punpcklwd xmm15, xmm15 
	punpcklwd xmm15, xmm15

	changePIxValue:
		movq xmm0, qword ptr[rcx+rsi]			;first 8 elements
		movq xmm1, qword ptr[rcx+rsi+8]			;next 8
		movq xmm2, qword ptr[rcx+rsi+16]		;next 8 - 8 pixels has been read
	
		movups xmm6, xmm0						;we're about to change registers xmm0, 1, 2 so that in xmm0 are all Red colors so we have to remember those registers' content
		movups xmm7, xmm1
		movups xmm8, xmm2
	
		movups xmm3, XMMWORD PTR R_First		;moving locally declared arrays into xmm registers - here are array describing indexes of Red colors (8 bytes)
		movups xmm4, XMMWORD PTR R_Second		;same with next 8 bytes
		movups xmm5, XMMWORD PTR R_Third		;same with last 8 bytes
		pshufb xmm0, xmm3						;now in xmm0 will be left only elements representing Red colors		
		pshufb xmm1,xmm4						;same with next bytes
		pshufb xmm2,xmm5						;same with next bytes
		paddusb xmm0, xmm1						;summing all three registers
		paddusb xmm0, xmm2						;XMM0 REGISTER CONTAINS ONLY RED COLORS OF 8 PIXELS

		movups xmm1, xmm6						;we're about to change registers xmm6, 7, 8 so that in xmm0 are all Red colors so we have to remember those registers' content
		movups xmm2, xmm7
		movups xmm9, xmm8

		movups xmm3, XMMWORD PTR G_First		;moving locally declared arrays into xmm registers - here are array describing indexes of Green colors (8 bytes)
		movups xmm4, XMMWORD PTR G_Second		;same with next 8 bytes
		movups xmm5, XMMWORD PTR G_Third		;same with next 8 bytes
		pshufb xmm6, xmm3						;now in xmm6 will be left only elements representing Red colors	
		pshufb xmm7,xmm4						;same with next bytes
		pshufb xmm8,xmm5						;same with next bytes
		paddusb xmm6, xmm7						;summing all thre registers
		paddusb xmm6, xmm8						;XMM0 REGISTER CONTAINS ONLY GREEN COLORS OF 8 PIXELS

		movups xmm3, XMMWORD PTR B_First		;moving locally declared arrays into xmm registers - here are array describing indexes of Blue colors (8 bytes)
		movups xmm4, XMMWORD PTR B_Second		;same with next 8 bytes
		movups xmm5, XMMWORD PTR B_Third		;same with next 8 bytes
		pshufb xmm1, xmm3						;now in xmm1 will be left only elements representing Red colors	
		pshufb xmm2,xmm4						;same with next bytes
		pshufb xmm9,xmm5						;same with next bytes
		paddusb xmm1, xmm2						;summing all thre registers
		paddusb xmm1, xmm9						;XMM1 REGISTER CONTAINS ONLY BLUE COLORS OF 8 PIXELS

		; xmm0 - R values of 8 colors
		; xmm6 - G values of 8 colors
		; xmm1 - B values of 8 colors

		pxor xmm3, xmm3							;resetting xmm3 so we can add to it R, G, B registers we created above
		paddusw xmm3,xmm0						;adding R, G, B values
		paddusw xmm3, xmm1
		paddusw xmm3, xmm6						;xmm3 register contains sum of 8 pixels subdivisions (R, G, B values)
		pcmpgtw xmm3, xmm15						;comparing sum of one pixel to global average multiplied by three and writing 0 or 255 depending on the result

		movups xmm1, xmm3						;moving sum of one pixel to another register because we're about to change xmm3 register
		movups xmm0, XMMWORD PTR FirstEightEl	;moving locally declared array into xmm register - here are array describing indexes of first eight elements we must re-write into bitmap bytes array
		pshufb xmm3, xmm0						;on lower half of xmm3 register there are 8 elements of array changed either into 0 or 255
		vmovq qword ptr [rcx+rsi], xmm3			;moving those eight elements into actual array that's going to be read from outside assembly function

		add rsi, 8								;next 8 elements
		movups xmm3, xmm1						;moving sum of one pixel to another register because we're about to change xmm1 register
		movups xmm0, XMMWORD PTR SecondEightEl	;moving locally declared array into xmm register - here are array describing indexes of second eight elements we must re-write into bitmap bytes array
		pshufb xmm1, xmm0						;on lower half of xmm1 register there are 8 elements of array changed either into 0 or 255
		vmovq qword ptr [rcx+rsi], xmm1			;moving those eight elements into actual array that's going to be read from outside assembly function

		add rsi, 8								;next 8 elements
		movups xmm0, XMMWORD PTR ThirdEightEl	;moving locally declared array into xmm register - here are array describing indexes of third eight elements we must re-write into bitmap bytes array
		pshufb xmm3, xmm0						;on lower half of xmm3 register there are 8 elements of array changed either into 0 or 255
		vmovq qword ptr [rcx+rsi], xmm3			;moving those eight elements into actual array that's going to be read from outside assembly function

		add rsi, 8								;preparing counter for next iteration

		cmp rsi,r8								;checking if the end of the range hadn't been reached
		jb changePIxValue						;if not, execute this loop again

	add r8,24									;back to actual end of the range
	cmp r8, rsi									;checking if there are any elements left
	je endAlg									;if not, the algorithm is over
	jnb editRest								;otherwise there must be edited as well but without using SSE operations

	editRest:
		xor rax, rax							;resetting an accumulator (in which the local sum will be stored)
		mov bl, byte ptr[rcx+rsi]				;moving one element from byte's array (which means one color of one pixel)
		add rax,rbx								;adding it to local sum
		inc rsi									;incrementing counter
		mov bl, byte ptr[rcx+rsi]				;same with next two elements
		add rax,rbx 
		inc rsi
		mov bl, byte ptr[rcx+rsi] 
		add rax,rbx 
		sub rsi, 2								;back to rsi before adding local sum elements; this is needed to write properly new data into rcx array
		cmp rax, r10							;checking what color should be pixels (comparing local sum with global average multiplied by 3)
		jnb  white
		jmp black
		
		white: 
				mov byte ptr[rcx+rsi], 255		;moving 255 (white color) into one pixel (three elements)
				inc rsi
				mov byte ptr[rcx+rsi], 255
				inc rsi
				mov byte ptr[rcx+rsi], 255
				inc rsi
				jmp continue

		black: 
				mov byte ptr[rcx+rsi], 0		;moving 0 (black color) into one pixel (three elements)
				inc rsi
				mov byte ptr[rcx+rsi], 0
				inc rsi
				mov byte ptr[rcx+rsi], 0
				inc rsi

		continue: 
			cmp r8,rsi							;checking if there are aby elements left
			je endAlg							;if not, the algorithm is finished
			jmp editRest						;otherwise they must be edited as well

	endAlg: ret 
EditArrayASMSIMD endp


END
