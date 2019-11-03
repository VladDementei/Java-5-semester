Program comments;
{$ private}
{{}
{a$a}
  Var a,b:Integer;  // тут строчный комментарий
  Var s:String;
  Begin
    // WriteLn('Введите число');
	a:=0;
	b:=10;
	S:= 'He{ll{o{' {комментарий} ' w}or}ld//not comment'(**);
	S:= 'H(*el(*lo(*' (*(*комментарий***) ' w)*or*)ld//not comment';
	(**(*for a:=1 to 10 do
	{}
	  begin
	    Write (a*b, ' ');
	  end;
	 все что внутри не исполняется*)
	 {  Это блочный комментарий
	 (**)
	for a:=1 to 10 do{
	  begin
	    Write (a*b, ' ');
	  end;}
	  Writeln (a+b, 'he(** hg *)llo ''');
	  Writeln ('hg{b}gh' {комменатрий} '{y}');
	  Writeln ('//');
	  Writeln ('(**)');
	  Writeln(s);
  End.
