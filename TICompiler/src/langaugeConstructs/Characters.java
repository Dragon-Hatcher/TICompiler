package langaugeConstructs;

public enum Characters {
	recursive_n,
	recursive_u,
	recursive_v,
	recursive_w,
	convert,
	square_up,
	square_down,
	integral,
	cross,
	box_icon,
	cross_icon,
	dot_icon,
	subscript_T,
	cube_root,
	hex_f,
	root,
	inverse,
	square,
	angle,
	degree,
	radian,
	transpose,
	less_than_or_equal_to,
	not_equal_to,
	greater_than_or_equal_to,
	negation,
	expoinent,
	store,
	ten,
	up_arrow,
	down_arrow,
	space,
	exclamation,
	quote,
	pound_sign,
	fourth,
	percent_sign,
	ampersand,
	apostrophe,
	left_parenthesis,
	right_parenthesis,
	asterix,
	plus_sign,
	comma,
	dash,
	period,
	slash,
	n0,
	n1,
	n2,
	n3,
	n4,
	n5,
	n6,
	n7,
	n8,
	n9,
	colon,
	semicolon,
	less_than,
	equal_to,
	greater_than,
	question_mark,
	at_sign,
	A,
	B,
	C,
	D,
	E,
	F,
	G,
	H,
	I,
	J,
	K,
	L,
	M,
	N,
	O,
	P,
	Q,
	R,
	S,
	T,
	U,
	V,
	W,
	X,
	Y,
	Z,
	theta,
	backslash,
	right_bracket,
	caret,
	underscore,
	backquote,
	a,
	b,
	c,
	d,
	e,
	f,
	g,
	h,
	i,
	j,
	k,
	l,
	m,
	n,
	o,
	p,
	q,
	r,
	s,
	t,
	u,
	v,
	w,
	x,
	y,
	z,
	left_brace,
	pipe,
	right_brace,
	tilde,
	inverse_equal_to,
	subscript_0,
	subscript_1,
	subscript_2,
	subscript_3,
	subscript_4,
	subscript_5,
	subscript_6,
	subscript_7,
	subscript_8,
	subscript_9,
	A_acute,
	A_grave,
	A_caret,
	A_diaeresis,
	a_acute,
	a_grave,
	a_caret,
	a_diaeresis,
	E_acute,
	E_grave,
	E_caret,
	E_diaeresis,
	e_acute,
	e_grave,
	e_caret,
	e_diaeresis,
	I_acute,
	I_grave,
	I_caret,
	I_diaeresis,
	i_acute,
	i_grave,
	i_caret,
	i_diaeresis,
	O_acute,
	O_grave,
	O_caret,
	O_diaeresis,
	o_acute,
	o_grave,
	o_caret,
	o_diaeresis,
	U_acute,
	U_grave,
	U_caret,
	U_diaeresis,
	u_acute,
	u_grave,
	u_caret,
	u_diaeresis,
	C_cedilla,
	c_cedilla,
	N_tilde,
	n_tilde,
	accent,
	grave,
	diaeresis,
	inverted_question_mark,
	inverted_exclamtion_mark,
	small_alpha,
	small_beta,
	small_gamma,
	capital_delta,
	small_delta,
	small_epsilon,
	left_bracket,
	small_lamba,
	small_mu,
	small_pi,
	small_rho,
	capital_sigma,
	small_sigma,
	small_tau,
	small_phi,
	capital_omega,
	x_mean,
	y_mean,
	super_x,
	ellipsis,
	left_triangle,
	block,
	per,
	hypen,
	area,
	temperature,
	cube,
	enter,
	imaginary_i,
	p_hat,
	small_chi,
	stat_f,
	natural_log_e,
	list_L,
	finance_N,
	two_right_parentheses,
	block_arrow,
	cursor_overwrite,
	cursor_overwrite_second,
	cursor_overwrite_A,
	cursor_overwrite_a,
	cursor_insert,
	cursor_insert_second,
	cursor_insert_A,
	cursor_insert_a,
	graph_line,
	graph_line_thick,
	graph_above,
	graph_below,
	graph_path,
	graph_animate,
	graph_dot,
	up_block,
	down_block,
	cursor_full,
	dollar_sign,
	sharp_s,
	mathprint_mixed_fraction_separator,
	mathprint_fraction_slash,
	mathprint_entry_box;
	
	public static Characters stringToCharacter(String in) {
		switch(in) {
		 case "\\rec n\\": return Characters.recursive_n;
		 case "\\rec u\\": return Characters.recursive_u;
		 case "\\rec v\\": return Characters.recursive_v;
		 case "\\rec w\\": return Characters.recursive_w;
		 case "\\convert arrow\\": return Characters.convert;
		 case "\\big up arrow\\": return Characters.square_up;
		 case "\\big down arrow\\": return Characters.square_down;
		 case "\\integral\\": return Characters.integral;
		 case "\\cross\\": return Characters.cross;
		 case "\\box\\": return Characters.box_icon;
		 case "\\small plus\\": return Characters.cross_icon;
		 case "\\dot\\": return Characters.dot_icon;
		 case "\\sub T\\": return Characters.subscript_T;
		 case "\\cube root\\": return Characters.cube_root;
		 case "\\hex F\\": return Characters.hex_f;
		 case "\\root\\": return Characters.root;
		 case "\\inverse\\": return Characters.inverse;
		 case "\\pow 2\\": return Characters.square;
		 case "\\angle\\": return Characters.angle;
		 case "\\degree\\": return Characters.degree;
		 case "\\radian\\": return Characters.radian;
		 case "\\transpose\\": return Characters.transpose;
		 case "\\<=\\": return Characters.less_than_or_equal_to;
		 case "\\!=\\": return Characters.not_equal_to;
		 case "\\>=\\": return Characters.greater_than_or_equal_to;
		 case "\\negate\\": return Characters.negation;
		 case "\\exponent\\": return Characters.expoinent;
		 case "\\right arrow\\": return Characters.store;
		 case "\\ten\\": return Characters.ten;
		 case "\\up arrow\\": return Characters.up_arrow;
		 case "\\down arrow\\": return Characters.down_arrow;
		 case " ": return Characters.space;
		 case "!": return Characters.exclamation;
		 case "\\\"\\": return Characters.quote;
		 case "#": return Characters.pound_sign;
		 case "\\pow 4\\": return Characters.fourth;
		 case "%": return Characters.percent_sign;
		 case "&": return Characters.ampersand;
		 case "'": return Characters.apostrophe;
		 case "(": return Characters.left_parenthesis;
		 case ")": return Characters.right_parenthesis;
		 case "*": return Characters.asterix;
		 case "+": return Characters.plus_sign;
		 case ",": return Characters.comma;
		 case "\\dash\\": return Characters.dash;
		 case ".": return Characters.period;
		 case "/": return Characters.slash;
		 case "0": return Characters.n0;
		 case "1": return Characters.n1;
		 case "2": return Characters.n2;
		 case "3": return Characters.n3;
		 case "4": return Characters.n4;
		 case "5": return Characters.n5;
		 case "6": return Characters.n6;
		 case "7": return Characters.n7;
		 case "8": return Characters.n8;
		 case "9": return Characters.n9;
		 case ":": return Characters.colon;
		 case ";": return Characters.semicolon;
		 case "<": return Characters.less_than;
		 case "=": return Characters.equal_to;
		 case ">": return Characters.greater_than;
		 case "?": return Characters.question_mark;
		 case "@": return Characters.at_sign;
		 case "A": return Characters.A;
		 case "B": return Characters.B;
		 case "C": return Characters.C;
		 case "D": return Characters.D;
		 case "E": return Characters.E;
		 case "F": return Characters.F;
		 case "G": return Characters.G;
		 case "H": return Characters.H;
		 case "I": return Characters.I;
		 case "J": return Characters.J;
		 case "K": return Characters.K;
		 case "L": return Characters.L;
		 case "M": return Characters.M;
		 case "N": return Characters.N;
		 case "O": return Characters.O;
		 case "P": return Characters.P;
		 case "Q": return Characters.Q;
		 case "R": return Characters.R;
		 case "S": return Characters.S;
		 case "T": return Characters.T;
		 case "U": return Characters.U;
		 case "V": return Characters.V;
		 case "W": return Characters.W;
		 case "X": return Characters.Z;
		 case "Y": return Characters.Y;
		 case "Z": return Characters.Z;
		 case "\\theta\\": return Characters.theta;
		 case "\\backslash\\": return Characters.backslash;
		 case "]": return Characters.right_bracket;
		 case "^": return Characters.caret;
		 case "_": return Characters.underscore;
		 case "`": return Characters.backquote;
		 case "a": return Characters.a;
		 case "b": return Characters.b;
		 case "c": return Characters.c;
		 case "d": return Characters.d;
		 case "e": return Characters.e;
		 case "f": return Characters.f;
		 case "g": return Characters.g;
		 case "h": return Characters.h;
		 case "i": return Characters.i;
		 case "j": return Characters.j;
		 case "k": return Characters.k;
		 case "l": return Characters.l;
		 case "m": return Characters.m;
		 case "n": return Characters.n;
		 case "o": return Characters.o;
		 case "p": return Characters.p;
		 case "q": return Characters.q;
		 case "r": return Characters.r;
		 case "s": return Characters.s;
		 case "t": return Characters.t;
		 case "u": return Characters.u;
		 case "v": return Characters.v;
		 case "w": return Characters.w;
		 case "x": return Characters.x;
		 case "y": return Characters.y;
		 case "z": return Characters.z;
		 case "{": return Characters.left_brace;
		 case "|": return Characters.pipe;
		 case "}": return Characters.right_brace;
		 case "~": return Characters.tilde;
		 case "\\inverted =\\": return Characters.inverse_equal_to;
		 case "\\sub 0\\": return Characters.subscript_0;
		 case "\\sub 1\\": return Characters.subscript_1;
		 case "\\sub 2\\": return Characters.subscript_2;
		 case "\\sub 3\\": return Characters.subscript_3;
		 case "\\sub 4\\": return Characters.subscript_4;
		 case "\\sub 5\\": return Characters.subscript_5;
		 case "\\sub 6\\": return Characters.subscript_6;
		 case "\\sub 7\\": return Characters.subscript_7;
		 case "\\sub 8\\": return Characters.subscript_8;
		 case "\\sub 9\\": return Characters.subscript_9;
		 case "\\acute A\\": return Characters.A_acute;
		 case "\\grave A\\": return Characters.A_grave;
		 case "\\caret A\\": return Characters.A_caret;
		 case "\\diaeresis A\\": return Characters.A_diaeresis;
		 case "\\acute a\\": return Characters.a_acute;
		 case "\\grave a\\": return Characters.a_grave;
		 case "\\caret a\\": return Characters.a_caret;
		 case "\\diaeresis a\\": return Characters.a_diaeresis;
		 case "\\acute E\\": return Characters.E_acute;
		 case "\\grave E\\": return Characters.E_grave;
		 case "\\caret E\\": return Characters.E_caret;
		 case "\\diaeresis E\\": return Characters.E_diaeresis;
		 case "\\acute e\\": return Characters.e_acute;
		 case "\\grave e\\": return Characters.e_grave;
		 case "\\caret e\\": return Characters.e_caret;
		 case "\\diaeresis e\\": return Characters.e_diaeresis;
		 case "\\acute I\\": return Characters.I_acute;
		 case "\\grave I\\": return Characters.I_grave;
		 case "\\caret I\\": return Characters.I_caret;
		 case "\\diaeresis I\\": return Characters.I_diaeresis;
		 case "\\acute i\\": return Characters.i_acute;
		 case "\\grave i\\": return Characters.i_grave;
		 case "\\caret i\\": return Characters.i_caret;
		 case "\\diaeresis i\\": return Characters.i_diaeresis;
		 case "\\acute O\\": return Characters.O_acute;
		 case "\\grave O\\": return Characters.O_grave;
		 case "\\caret O\\": return Characters.O_caret;
		 case "\\diaeresis O\\": return Characters.O_diaeresis;
		 case "\\acute o\\": return Characters.o_acute;
		 case "\\grave o\\": return Characters.o_grave;
		 case "\\caret o\\": return Characters.o_caret;
		 case "\\diaeresis o\\": return Characters.o_diaeresis;
		 case "\\acute U\\": return Characters.U_acute;
		 case "\\grave U\\": return Characters.U_grave;
		 case "\\caret U\\": return Characters.U_caret;
		 case "\\diaeresis U\\": return Characters.U_diaeresis;
		 case "\\acute u\\": return Characters.u_acute;
		 case "\\grave u\\": return Characters.u_grave;
		 case "\\caret u\\": return Characters.u_caret;
		 case "\\diaeresis u\\": return Characters.u_diaeresis;
		 case "\\cedilla C\\": return Characters.C_cedilla;
		 case "\\cedilla c\\": return Characters.c_cedilla;
		 case "\\tilde N\\": return Characters.N_tilde;
		 case "\\tilde n\\": return Characters.n_tilde;
		 case "\\accent\\": return Characters.accent;
		 case "\\grave\\": return Characters.grave;
		 case "\\diaeresis\\": return Characters.diaeresis;
		 case "\\inverted ?\\": return Characters.inverted_question_mark;
		 case "\\inverted !\\": return Characters.inverted_exclamtion_mark;
		 case "\\alpha\\": return Characters.small_alpha;
		 case "\\beta\\": return Characters.small_beta;
		 case "\\gama\\": return Characters.small_gamma;
		 case "\\capital delta\\": return Characters.capital_delta;
		 case "\\small delta\\": return Characters.small_delta;
		 case "\\epsilon\\": return Characters.small_epsilon;
		 case "[": return Characters.left_bracket;
		 case "\\lamba\\": return Characters.small_lamba;
		 case "\\mu\\": return Characters.small_mu;
		 case "\\pi\\": return Characters.small_pi;
		 case "\\rho\\": return Characters.small_rho;
		 case "\\capital sigma\\": return Characters.capital_sigma;
		 case "\\sigma\\": return Characters.small_sigma;
		 case "\\tau\\": return Characters.small_tau;
		 case "\\phi\\": return Characters.small_phi;
		 case "\\omega\\": return Characters.capital_omega;
		 case "\\x mean\\": return Characters.x_mean;
		 case "\\y mean\\": return Characters.y_mean;
		 case "\\super x\\": return Characters.super_x;
		 case "\\...\\": return Characters.ellipsis;
		 case "\\left triangle\\": return Characters.left_triangle;
		 case "\\block\\": return Characters.block;
		 case "\\per\\": return Characters.per;
		 case "-": return Characters.hypen;
		 case "\\area\\": return Characters.area;
		 case "\\temperature\\": return Characters.temperature;
		 case "\\cube\\": return Characters.cube;
		 case "\n": return Characters.enter;
		 case "\\i\\": return Characters.imaginary_i;
		 case "\\p hat\\": return Characters.p_hat;
		 case "\\chi\\": return Characters.small_chi;
		 case "\\stat F\\": return Characters.stat_f;
		 case "\\e\\": return Characters.natural_log_e;
		 case "\\list L\\": return Characters.list_L;
		 case "\\finance N\\": return Characters.finance_N;
		 case "\\))\\": return Characters.two_right_parentheses;
		 case "\\thick right arrow\\": return Characters.block_arrow;
		 case "\\cursor\\": return Characters.cursor_overwrite;
		 case "\\cursor arrow\\": return Characters.cursor_overwrite_second;
		 case "\\cursor A\\": return Characters.cursor_overwrite_A;
		 case "\\cursor a\\": return Characters.cursor_overwrite_a;
		 case "\\cursor _\\": return Characters.cursor_insert;
		 case "\\cursor _ arrow\\": return Characters.cursor_insert_second;
		 case "\\cursor _ A\\": return Characters.cursor_insert_A;
		 case "\\cursor _ a\\": return Characters.cursor_insert_a;
		 case "\\line\\": return Characters.graph_line;
		 case "\\thick line\\": return Characters.graph_line_thick;
		 case "\\above line\\": return Characters.graph_above;
		 case "\\below line\\": return Characters.graph_below;
		 case "\\line end\\": return Characters.graph_path;
		 case "\\line dot\\": return Characters.graph_animate;
		 case "\\dot line\\": return Characters.graph_dot;
		 case "\\long up arrow\\": return Characters.up_block;
		 case "\\long down arror\\": return Characters.down_block;
		 case "\\dither\\": return Characters.cursor_full;
		 case "$": return Characters.dollar_sign;
		 case "\\sharp S\\": return Characters.sharp_s;
		 case "\\math seperator\\": return Characters.mathprint_mixed_fraction_separator;
		 case "\\math /\\": return Characters.mathprint_fraction_slash;
		 case "\\dot box\\": return Characters.mathprint_entry_box;
		 default:
			 throw new IllegalArgumentException("There is no character with code " + in + ".");
		}
	}
	
	public int charToCode() {
		switch(this) {
		case recursive_n                        : return 0x1;
		case recursive_u                        : return 0x2;
		case recursive_v                        : return 0x3;
		case recursive_w                        : return 0x4;
		case convert                            : return 0x5;
		case square_up                          : return 0x6;
		case square_down                        : return 0x7;
		case integral                           : return 0x8;
		case cross                              : return 0x9;
		case box_icon                           : return 0xa;
		case cross_icon                         : return 0xb;
		case dot_icon                           : return 0xc;
		case subscript_T                        : return 0xd;
		case cube_root                          : return 0xe;
		case hex_f                              : return 0xf;
		case root                               : return 0x10;
		case inverse                            : return 0x11;
		case square                             : return 0x12;
		case angle                              : return 0x13;
		case degree                             : return 0x14;
		case radian                             : return 0x15;
		case transpose                          : return 0x16;
		case less_than_or_equal_to              : return 0x17;
		case not_equal_to                       : return 0x18;
		case greater_than_or_equal_to           : return 0x19;
		case negation                           : return 0x1a;
		case expoinent                          : return 0x1b;
		case store                              : return 0x1c;
		case ten                                : return 0x1d;
		case up_arrow                           : return 0x1e;
		case down_arrow                         : return 0x1f;
		case space                              : return 0x20;
		case exclamation                        : return 0x21;
		case quote                              : return 0x22;
		case pound_sign                         : return 0x23;
		case fourth                             : return 0x24;
		case percent_sign                       : return 0x25;
		case ampersand                          : return 0x26;
		case apostrophe                         : return 0x27;
		case left_parenthesis                   : return 0x28;
		case right_parenthesis                  : return 0x29;
		case asterix                            : return 0x2a;
		case plus_sign                          : return 0x2b;
		case comma                              : return 0x2c;
		case dash                               : return 0x2d;
		case period                             : return 0x2e;
		case slash                              : return 0x2f;
		case n0                                 : return 0x30;
		case n1                                 : return 0x31;
		case n2                                 : return 0x32;
		case n3                                 : return 0x33;
		case n4                                 : return 0x34;
		case n5                                 : return 0x35;
		case n6                                 : return 0x36;
		case n7                                 : return 0x37;
		case n8                                 : return 0x38;
		case n9                                 : return 0x39;
		case colon                              : return 0x3a;
		case semicolon                          : return 0x3b;
		case less_than                          : return 0x3c;
		case equal_to                           : return 0x3d;
		case greater_than                       : return 0x3e;
		case question_mark                      : return 0x3f;
		case at_sign                            : return 0x40;
		case A                                  : return 0x41;
		case B                                  : return 0x42;
		case C                                  : return 0x43;
		case D                                  : return 0x44;
		case E                                  : return 0x45;
		case F                                  : return 0x46;
		case G                                  : return 0x47;
		case H                                  : return 0x48;
		case I                                  : return 0x49;
		case J                                  : return 0x4a;
		case K                                  : return 0x4b;
		case L                                  : return 0x4c;
		case M                                  : return 0x4d;
		case N                                  : return 0x4e;
		case O                                  : return 0x4f;
		case P                                  : return 0x50;
		case Q                                  : return 0x51;
		case R                                  : return 0x52;
		case S                                  : return 0x53;
		case T                                  : return 0x54;
		case U                                  : return 0x55;
		case V                                  : return 0x56;
		case W                                  : return 0x57;
		case X                                  : return 0x58;
		case Y                                  : return 0x59;
		case Z                                  : return 0x5a;
		case theta                              : return 0x5b;
		case backslash                          : return 0x5c;
		case right_bracket                      : return 0x5d;
		case caret                              : return 0x5e;
		case underscore                         : return 0x5f;
		case backquote                          : return 0x60;
		case a                                  : return 0x61;
		case b                                  : return 0x62;
		case c                                  : return 0x63;
		case d                                  : return 0x64;
		case e                                  : return 0x65;
		case f                                  : return 0x66;
		case g                                  : return 0x67;
		case h                                  : return 0x68;
		case i                                  : return 0x69;
		case j                                  : return 0x6a;
		case k                                  : return 0x6b;
		case l                                  : return 0x6c;
		case m                                  : return 0x6d;
		case n                                  : return 0x6e;
		case o                                  : return 0x6f;
		case p                                  : return 0x70;
		case q                                  : return 0x71;
		case r                                  : return 0x72;
		case s                                  : return 0x73;
		case t                                  : return 0x74;
		case u                                  : return 0x75;
		case v                                  : return 0x76;
		case w                                  : return 0x77;
		case x                                  : return 0x78;
		case y                                  : return 0x79;
		case z                                  : return 0x7a;
		case left_brace                         : return 0x7b;
		case pipe                               : return 0x7c;
		case right_brace                        : return 0x7d;
		case tilde                              : return 0x7e;
		case inverse_equal_to                   : return 0x7f;
		case subscript_0                        : return 0x80;
		case subscript_1                        : return 0x81;
		case subscript_2                        : return 0x82;
		case subscript_3                        : return 0x83;
		case subscript_4                        : return 0x84;
		case subscript_5                        : return 0x85;
		case subscript_6                        : return 0x86;
		case subscript_7                        : return 0x87;
		case subscript_8                        : return 0x88;
		case subscript_9                        : return 0x89;
		case A_acute                            : return 0x8a;
		case A_grave                            : return 0x8b;
		case A_caret                            : return 0x8c;
		case A_diaeresis                        : return 0x8d;
		case a_acute                            : return 0x8e;
		case a_grave                            : return 0x8f;
		case a_caret                            : return 0x90;
		case a_diaeresis                        : return 0x91;
		case E_acute                            : return 0x92;
		case E_grave                            : return 0x93;
		case E_caret                            : return 0x94;
		case E_diaeresis                        : return 0x95;
		case e_acute                            : return 0x96;
		case e_grave                            : return 0x97;
		case e_caret                            : return 0x98;
		case e_diaeresis                        : return 0x99;
		case I_acute                            : return 0x9a;
		case I_grave                            : return 0x9b;
		case I_caret                            : return 0x9c;
		case I_diaeresis                        : return 0x9d;
		case i_acute                            : return 0x9e;
		case i_grave                            : return 0x9f;
		case i_caret                            : return 0xa0;
		case i_diaeresis                        : return 0xa1;
		case O_acute                            : return 0xa2;
		case O_grave                            : return 0xa3;
		case O_caret                            : return 0xa4;
		case O_diaeresis                        : return 0xa5;
		case o_acute                            : return 0xa6;
		case o_grave                            : return 0xa7;
		case o_caret                            : return 0xa8;
		case o_diaeresis                        : return 0xa9;
		case U_acute                            : return 0xaa;
		case U_grave                            : return 0xab;
		case U_caret                            : return 0xac;
		case U_diaeresis                        : return 0xad;
		case u_acute                            : return 0xae;
		case u_grave                            : return 0xaf;
		case u_caret                            : return 0xb0;
		case u_diaeresis                        : return 0xb1;
		case C_cedilla                          : return 0xb2;
		case c_cedilla                          : return 0xb3;
		case N_tilde                            : return 0xb4;
		case n_tilde                            : return 0xb5;
		case accent                             : return 0xb6;
		case grave                              : return 0xb7;
		case diaeresis                          : return 0xb8;
		case inverted_question_mark             : return 0xb9;
		case inverted_exclamtion_mark           : return 0xba;
		case small_alpha                        : return 0xbb;
		case small_beta                         : return 0xbc;
		case small_gamma                        : return 0xbd;
		case capital_delta                      : return 0xbe;
		case small_delta                        : return 0xbf;
		case small_epsilon                      : return 0xc0;
		case left_bracket                       : return 0xc1;
		case small_lamba                        : return 0xc2;
		case small_mu                           : return 0xc3;
		case small_pi                           : return 0xc4;
		case small_rho                          : return 0xc5;
		case capital_sigma                      : return 0xc6;
		case small_sigma                        : return 0xc7;
		case small_tau                          : return 0xc8;
		case small_phi                          : return 0xc9;
		case capital_omega                      : return 0xca;
		case x_mean                             : return 0xcb;
		case y_mean                             : return 0xcc;
		case super_x                            : return 0xcd;
		case ellipsis                           : return 0xce;
		case left_triangle                      : return 0xcf;
		case block                              : return 0xd0;
		case per                                : return 0xd1;
		case hypen                              : return 0xd2;
		case area                               : return 0xd3;
		case temperature                        : return 0xd4;
		case cube                               : return 0xd5;
		case enter                              : return 0xd6;
		case imaginary_i                        : return 0xd7;
		case p_hat                              : return 0xd8;
		case small_chi                          : return 0xd9;
		case stat_f                             : return 0xda;
		case natural_log_e                      : return 0xdb;
		case list_L                             : return 0xdc;
		case finance_N                          : return 0xdd;
		case two_right_parentheses              : return 0xde;
		case block_arrow                        : return 0xdf;
		case cursor_overwrite                   : return 0xe0;
		case cursor_overwrite_second            : return 0xe1;
		case cursor_overwrite_A                 : return 0xe2;
		case cursor_overwrite_a                 : return 0xe3;
		case cursor_insert                      : return 0xe4;
		case cursor_insert_second               : return 0xe5;
		case cursor_insert_A                    : return 0xe6;
		case cursor_insert_a                    : return 0xe7;
		case graph_line                         : return 0xe8;
		case graph_line_thick                   : return 0xe9;
		case graph_above                        : return 0xea;
		case graph_below                        : return 0xeb;
		case graph_path                         : return 0xec;
		case graph_animate                      : return 0xed;
		case graph_dot                          : return 0xee;
		case up_block                           : return 0xef;
		case down_block                         : return 0xf0;
		case cursor_full                        : return 0xf1;
		case dollar_sign                        : return 0xf2;
		case sharp_s                            : return 0xf4;
		case mathprint_mixed_fraction_separator : return 0xf5;
		case mathprint_fraction_slash           : return 0xf6;
		case mathprint_entry_box                : return 0xf7;
		}
		return 0;
	}
}
