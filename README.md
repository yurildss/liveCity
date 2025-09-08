# 🌆 LiveCity (Ainda em desenvolvimento) 

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
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/dc67703e-ce08-443d-8e8f-7c826a7b4eb8" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/1381e7a8-237a-4772-99d1-48fcb8c4231f" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/d0447411-4041-42c4-9c19-730e6f7dc9e7" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/c8ff690a-4359-4f5b-9d5e-23d1343ad5b9" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/fad6fb5f-c5b0-4eec-a83e-cd51bfb152d1" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/e946b2aa-3bfa-478b-825c-50848d4d9c98" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/8fd6d599-4f26-418f-a821-0b2d38040714" />
<img width="216" height="480" alt="image" src="https://github.com/user-attachments/assets/5318ae2b-01cf-4127-89f6-f792a27b02bc" />


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
