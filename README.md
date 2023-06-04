Project eCourse
===============

[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=10488103&assignment_repo_type=AssignmentRepo)


# Description of the Project

*To Do*

## Project architecture

[Project architecture](./architecture.md)

# Planning and Technical Documentation

[Planning and Technical Documentation](docs/README.md)

# How to Build

Make sure Maven (`mvn`) is installed and in `$PATH`.

If using an Oracle database, you will need to change your maven settings for
downloading the Oracle drivers. See [this article](https://blogs.oracle.com/dev2dev/entry/how_to_get_oracle_jdbc#settings) for more information.

Run script:

- Linux, macOS, BSD, and other *NIX:
```bash
./rebuild-all.sh
```

- Windows:
```bat
.\rebuild-all.bat
```


# How to Execute Tests

Run script:

- Linux, macOS, BSD, and other *NIX:
```bash
./run-coverage.sh
```

- Windows:
```bat
.\run-coverage.bat
```


# How to Run

Make sure a **JRE** is installed and in `$PATH`.

Run script:

- Linux, macOS, BSD, and other *NIX:
```bash
# Teacher APP
./run-teacher.sh

# Student APP
./run-student.sh

# Manager APP
./run-manager.sh
```

- Windows:
```bat
REM Teacher APP
.\run-teacher.bat

REM Student APP
.\run-student.bat

REM Manager APP
.\run-manager.bat
```


# How to Install/Deploy into Another Machine (or Virtual Machine)

*To Do*

# How to Generate PlantUML Diagrams

To generate PlantUML diagrams for documentation execute the script (for the moment, only for linux/bsd/macos):

```bash
./generate-plantuml-diagrams.sh
```

# License Information

This project was created under [this pledge of honor](./pledge_of_honor.md)

- [EAPLI base](./LICENSE.eapli_base)
- [eCourse](./LICENSE)
