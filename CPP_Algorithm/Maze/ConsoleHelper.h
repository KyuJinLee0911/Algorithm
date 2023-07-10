#pragma once
#include <Windows.h>
#include "Types.h"

enum class ConsoleColor
{
	BLACK = 0,
	RED = FOREGROUND_RED,
	BLUE = FOREGROUND_BLUE,
	GREEN = FOREGROUND_GREEN,
	YELLOW = RED | GREEN,
	WHITE = RED | GREEN | BLUE
};

class ConsoleHelper
{
public:
	static void SetCursorPosition(int32 x, int32 y);
	static void SetCursorColor(ConsoleColor color);
	static void ShowConsoleCursor(bool flag);
};

