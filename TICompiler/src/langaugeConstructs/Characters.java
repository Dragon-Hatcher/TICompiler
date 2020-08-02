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
	
	Characters stringToCharacter(String in) {
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
		 case "\"": return Characters.quote;
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
}
