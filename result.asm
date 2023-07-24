INCLUDE Irvine32.inc
.data
aa SDWORD ?
a SDWORD ?
b SDWORD ?
.code
main PROC
mov a, 6
mov eax, a
mov ebx, 3
add eax, ebx
mov aa, eax
mov eax, aa
mov ebx, 2
imul eax, ebx
mov b, eax
mov eax, b
mov ebx, 2
add eax, ebx
mov eax, eax
call WriteDec
exit
main ENDP
END main
