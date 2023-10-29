# Web

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin) to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
    1) Run `Extensions: Show Built-in Extensions` from VSCode's command palette
    2) Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

# API

This is the backend part of the 5619 project assignment, primarily deployed using Spring Boot and Amazon Cloud.

## Build and Run

After Docker has been installed, follow the steps below:

1. Modify the API configuration file:
   - Open `API/src/main/resources/application.properties`
   - Configure the settings according to your environment
   - ```properties
     app.base-url=your-project-url
     spring.datasource.url=jdbc:mysql:your-mysql-url:3306/5619
     spring.datasource.username=yourusername
     spring.datasource.password=yourpassword
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect(or org.hibernate.dialect.MySQL8Dialect)
     ```
   
     
   
2. Modify the database configuration:
   - Open `API/src/main/resources/db/changelog/db.changelog-master.yaml`
   - Change `mysql-username` and `mysql-password` to your MySQL username and password
   - ```properties
     url: your-mysql-url
     username: your-mysql-username
     password: your-mysql-password
     ```
   
     
   
3. Run the `build.sh` script to build the Docker image and run the container:
   ```
   ./build.sh
   ```

## Rebuild and Rerun

If you need to rebuild and rerun the project, use the following command:
```
./rebuild.sh
```

