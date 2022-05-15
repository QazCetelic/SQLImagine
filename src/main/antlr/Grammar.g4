grammar Grammar;

// All spacing is ignored
SPACING : (' ' | '\t')+ -> skip;
COMMENT : (('#' ~'\n'*) | ('/*' (~'/')* '*/')) -> skip;

/*
    TYPES
*/
STRING: 'Str';
INTEGER: 'Int';
DECIMAL: 'Dec';
DATE: 'Date';
BOOL: 'Bool';

NULLABLE: '?';
type: STRING | INTEGER | DECIMAL | DATE | BOOL;
type_declare: '(' type (NULLABLE)? ')';

/*
    NAMES
*/
TABLE_NAME: [A-Z][a-zA-Z]*;
fragment ATTRIBUTE_NAME_SELF: [a-z](('_')? [a-z])*;
REFERENCED_ATTRIBUTE_NAME: TABLE_NAME '.' ATTRIBUTE_NAME_SELF;
ATTRIBUTE_NAME: (TABLE_NAME '.')? ATTRIBUTE_NAME_SELF;

/*
    DECLARATIONS
*/
PRIMARY_KEY_OPERATOR: '!';
REFERENCED_OPERATOR: '->';

// TODO reconsider allowing multiple references
reference_declare: REFERENCED_OPERATOR REFERENCED_ATTRIBUTE_NAME (',' REFERENCED_ATTRIBUTE_NAME)*;
attribute_declare: (PRIMARY_KEY_OPERATOR)? ATTRIBUTE_NAME type_declare (reference_declare)?;
table_declare: TABLE_NAME ':' ('\n' attribute_declare)+;

main: table_declare ('\n'+ table_declare)*;
