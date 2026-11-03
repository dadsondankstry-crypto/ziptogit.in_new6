# Personagem 3D — APK offline para Android

Projeto Android nativo simples que usa WebView para abrir um HTML 3D embutido. O app não solicita permissão de internet.

## O que faz
- Exibe um protótipo de manequim 3D de superfícies arredondadas feito em WebGL, com controle por toque.
- Permite variar a altura e a proporção da cabeça.
- Exporta um arquivo GLB real pelo seletor de documentos do Android.
- Funciona sem conexão depois da instalação (desde que o aparelho tenha WebView com WebGL).
- Ainda **não é** um humano fotorrealista e **não** tem esqueleto de animação.

## Gerar o APK no GitHub Actions
1. Crie um repositório GitHub, preferencialmente privado, com a branch principal `main`.
2. Envie os arquivos **extraídos**, mantendo os caminhos; `settings.gradle` deve ficar na raiz do repositório. **Não** suba só o ZIP.
3. O arquivo de workflow deve ficar em `.github/workflows/android.yml`.
4. Abra `Actions` > `Gerar APK Android` > `Run workflow` (ou abra a execução iniciada automaticamente no push).
5. Quando a execução terminar com sucesso, abra `Artifacts` e baixe `Personagem3D-APK`.
6. Extraia o ZIP do artefato e instale `app-debug.apk` no Android.
7. Ative o modo avião e teste a visualização e o botão de exportação.

## Se usar GitHub apenas pelo celular
Se a interface de upload não mantiver subpastas, crie os arquivos pela interface web do repositório usando `Add file` > `Create new file` e digite o caminho completo no nome (por exemplo `.github/workflows/android.yml`), colando o conteúdo do arquivo correspondente. Para arquivos extensos, considere usar um editor Git como github.dev em modo desktop ou um cliente Git no Android para enviar a pasta inteira. Em `Add file` > `Upload files` tente selecionar os arquivos extraídos sem perder os diretórios.

## Estrutura
```
.github/workflows/android.yml
app/src/main/assets/index.html
app/src/main/java/com/exemplo/personagem3d/MainActivity.java
app/src/main/AndroidManifest.xml
app/build.gradle
build.gradle
settings.gradle
gradle.properties
README.md
```

## Compilação
- GitHub Actions utiliza Java 17, Gradle 8.9, Android Gradle Plugin 8.7.3 e Android SDK 35.
- Geramos uma versão **debug** para testes. Para publicar na Play Store é necessário atualizar configurações de distribuição e criar uma assinatura release.
- **Não foi compilado nem testado em aparelho real neste ambiente**. O workflow precisa concluir uma execução para confirmar a compilação.
- Mantenha backup do projeto. Uma versão debug pode não atualizar diretamente outra versão assinada por uma chave diferente.
