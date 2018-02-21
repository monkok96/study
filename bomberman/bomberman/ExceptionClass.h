#ifndef EXCEPTIONCLASS_H
#define EXCEPTIONCLASS_H
#include <exception>

class BoardLoadingException : public std::exception {};
class TextureLoadingException : public std::exception {};
class FontLoadingException : public std::exception {};

#endif