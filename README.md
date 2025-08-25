# 🌆 LiveCity

O **LiveCity** é um aplicativo Android desenvolvido em **Kotlin + Jetpack Compose** que permite que os usuários **marquem ocorrências em tempo real no mapa da cidade**.  
Ele pode ser usado para reportar situações como:  
- 🚨 Assaltos ou tentativas de roubo  
- 🌳 Queda de árvores  
- 🚧 Calçadas/ruas danificadas  
- ⚡ Problemas de infraestrutura  

Assim, os cidadãos podem colaborar entre si e com órgãos públicos, criando uma comunidade mais segura e informada.  

---

## ✨ Funcionalidades
- 🗺️ Mapa interativo com **Google Maps Compose**  
- 📍 Adição de marcadores (ocorrências) por **toque longo** no mapa  
- 📌 **Clustering** de marcadores para melhor visualização  
- 🔍 Filtro e busca por cidade, estado e país  
- ⭐ Favoritar ocorrências  
- 🔒 **Firebase** (Auth + Firestore)  
- 🎨 **Material 3 + Jetpack Compose**  

---

## 🛠️ Tecnologias
**Kotlin**, **Jetpack Compose**, **Hilt (DI)**, **StateFlow + ViewModel (MVVM)**,  
**Firebase Authentication & Firestore**, **Google Maps Compose + Maps Utils (clustering)**.

---

## 📷 Protótipo / Screenshots
---

## 🚀 Como rodar o projeto

1. **Clone este repositório**
    
    ```bash
    git clone https://github.com/yurildss/LiveCity.git
    cd LiveCity
    ```

2. **Abra no Android Studio** (Giraffe/Koala ou superior).

3. **Configure o Firebase**
   - Baixe o `google-services.json` do seu projeto Firebase.
   - Coloque o arquivo em `app/google-services.json`.

4. **(Opcional) Configure a chave do Google Maps**
   - Use o plugin `secrets-gradle-plugin` e crie `secrets.properties` com:
     ```
     MAPS_API_KEY=SUAS_CHAVE_AQUI
     ```
   - No `AndroidManifest.xml`, dentro de `<application>`:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="${MAPS_API_KEY}" />
     ```

5. **Execute o app** em um dispositivo/emulador com Google Play Services.

---

## 📌 Futuras melhorias
- 🔔 Notificações de ocorrências próximas  
- 📷 Upload de fotos nas ocorrências  
- 👥 Perfil do usuário e histórico  
- 🏢 Integração com órgãos públicos  

---

## 👨‍💻 Autor
Desenvolvido por [**Yuri Lima**](https://github.com/yurildss) 💻🎸
