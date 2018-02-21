#include "dll_cpp.h"

#include <thread>
#include <vector>

/// Counts sum of bitmap array's elements starting with th "begining" index and ending with an "end" one
EXPORT void countSum(unsigned char* bitmapBytesArray, int begining, int end, uint64_t &sum)
{
	uint64_t sumLocal = 0; 
	for (int x = begining; x < end; ++x)
		sumLocal += bitmapBytesArray[x];
	sum = sumLocal;
}

/// Edits the bitmap array. If pixel's RGB average value is lower than the global average, those elements will be set to 0, otherwise - to 255 
EXPORT void editArray(unsigned char* bitmapBytesArray, int begining, int end, uint64_t average)
{
	uint64_t localSum = 0;
	average *= 3; // changing average into three elements' sum because division is an expensive operation
	for (int x = begining; x < end; x += 3)
	{
		localSum = bitmapBytesArray[x] + bitmapBytesArray[x + 1] + bitmapBytesArray[x + 2];
		if (localSum < average)
		{
			bitmapBytesArray[x] = bitmapBytesArray[x + 1] = bitmapBytesArray[x + 2] = 0;
		}
		else
		{
			bitmapBytesArray[x] = bitmapBytesArray[x + 1] = bitmapBytesArray[x + 2] = 255;
		}
	}
}

