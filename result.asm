INCLUDE Irvine32.inc
.data
Xval SDWORD ?
Yval SDWORD ?
Rval SDWORD ?
Zval SDWORD ?
.code
main PROC
mov Xval, 26
mov eax, Xval
mov ebx, 4
add eax, ebx
mov Yval, eax
mov Zval, 40
mov eax, Xval
mov ebx, Yval
mov ecx, Zval
sub ebx, ecx
add eax, ebx
mov ebx, 2
imul eax, ebx
mov Rval, eax
mov eax, Rval
mov eax, eax
call WriteDec
exit
main ENDP
END main
