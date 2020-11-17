# algamoney-api

# Features

REST services.

Database migration with FlyDB.

Abstract filter with predicates integrated with Page/Pageable.

# Problem using WSL 2

When running MySQL, logs aren't writing inside of /var/log/mysql/ directory.

To solve this, execute the code below:

```bash
# chmod u=rwx,g=rwx,o=rwx [file_or_directory_name]
chmod u=rwx,g=rwx,o=rwx /var/log/mysql/
```
