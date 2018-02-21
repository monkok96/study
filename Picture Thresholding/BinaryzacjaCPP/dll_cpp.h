#ifndef  DLL_CPP_H
#define DLL_CPP_H

#if defined BinaryzacjaCPP EXPORT
#endif
#define EXPORT __declspec(dllexport)
#include <cinttypes>

extern "C"
{
	EXPORT void countSum(unsigned char* bitmapBytesArray, int begining, int end, uint64_t &sum);
	EXPORT void editArray(unsigned char* bitmapBytesArray, int begining, int end, uint64_t average);
}

#endif // ! DLL_CPP_H
