# ![Logo](graphics/logo_32.svg) SQLImagine
A tool for quickly and easily designing SQL databases using a DSL and export it to [SQL](#sql-script-export).

## Domain Specific Language
### Syntax
SQLImagine allows for defining tables, attributes, and relationships.
I recommend using [YAML](https://yaml.org) syntax highlighting because the DSL's syntax has a few similarities.
The recommended file extension is `.sqli`.

```yaml
# It's possible to add comments.

Car:
  # The '!' means it's a primary key field.
  !name (Str)
  produced (Date)
  manufacturer (Str) -> Manufacturer.name
  color (Str)
  price (Dec)
  self_driving (Bool)
  
Manufacturer:
  name (Str)
  founded (Date)
  headquarters (Str)
  employees (Int)
  # The '?' signifies it's nullable.
  parent_company? (Str) -> Manufacturer.name
```

### Usage
Parsing is done using the `ParseInstance` class. It's created with a script as a string.

```kotlin
val scriptFile: File = …
val script: String = scriptFile.readText()
val parsed = ParseInstance(sqli)
// Print out a formatted version of the script.
println(parsed)
```

The parser is also able to reformat existing scripts by calling `.serialize()` on the created `ParseInstance`.
The class also has several accessible properties:

|  Property  | Description                                                      |
|:----------:|:-----------------------------------------------------------------|
|  `tables`  | All parsed tables                                                |
|  `errors`  | All errors, issues that prevent the script from properly parsing |
| `warnings` | All warnings, issues that aren't critical but do require changes |
| `success`  | Whether there are any critical issues                            |
|   `time`   | The amount of milliseconds it took to parse the DSL              |

## SQL script export
You can export the parsed DSL as a SQL script by using the `Sequelizer` class on the parsed DSL.

```kotlin
val sequelizer = Sequelizer("test_db")
val sql: String = sequelizer.sequelize(parsed)
```

![Example](graphics/usage.png)