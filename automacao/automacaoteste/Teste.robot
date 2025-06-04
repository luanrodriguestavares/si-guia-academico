*** Settings ***
Library    AppiumLibrary

*** Test Cases ***


Abrir app pelo launcher
    Open Application    http://127.0.0.1:4723/wd/hub
    ...    platformName=Android
    ...    deviceName=emulator-5554
    ...    appPackage=com.google.android.apps.nexuslauncher
    ...    appActivity=com.google.android.apps.nexuslauncher.NexusLauncherActivity
    ...    noReset=${True}
    ...    fullReset=${False}
        

    Wait Until Element Is Visible    xpath=//android.widget.TextView[@content-desc="Predicted app: Guia Acadêmico"]    10s
    Click Element    xpath=//android.widget.TextView[@content-desc="Predicted app: Guia Acadêmico"]

    Wait Until Page Contains    Sistemas de Informação    10
    Sleep    5s

    Wait Until Element Is Visible    xpath=//androidx.cardview.widget.CardView[@resource-id="com.fied.guiaacademico:id/cardGradeCurricular"]/android.widget.LinearLayout    10s
    Click Element    xpath=//androidx.cardview.widget.CardView[@resource-id="com.fied.guiaacademico:id/cardGradeCurricular"]/android.widget.LinearLayout
    Sleep    2

    Wait Until Element Is Visible    xpath=//androidx.recyclerview.widget.RecyclerView[@resource-id="com.fied.guiaacademico:id/rvDisciplinas"]/androidx.cardview.widget.CardView[2]/android.widget.LinearLayout/android.widget.ImageView    10s
    Click Element    xpath=//androidx.recyclerview.widget.RecyclerView[@resource-id="com.fied.guiaacademico:id/rvDisciplinas"]/androidx.cardview.widget.CardView[2]/android.widget.LinearLayout/android.widget.ImageView
    Sleep    2
    
    Swipe    890    2184    885    986

    Wait Until Element Is Visible    xpath=//android.widget.Button[@resource-id="com.fied.guiaacademico:id/btnAvaliar"]   10s
    Click Element    xpath=//android.widget.Button[@resource-id="com.fied.guiaacademico:id/btnAvaliar"]
    Sleep    2

    Wait Until Element Is Visible    xpath=//android.widget.RatingBar[@resource-id="com.fied.guiaacademico:id/ratingBar" and @text="0.0"]    10s
    Click Element    xpath=//android.widget.RatingBar[@resource-id="com.fied.guiaacademico:id/ratingBar" and @text="0.0"]


    Wait Until Element Is Visible    id=com.fied.guiaacademico:id/etComentario        10
    Input Text    id=com.fied.guiaacademico:id/etComentario    HAAAAAAAAAAAAAAAAAAAA
    Wait Until Element Is Visible    xpath=//android.widget.Button[@resource-id="com.fied.guiaacademico:id/btnEnviar"]   10s
    Click Element    xpath=//android.widget.Button[@resource-id="com.fied.guiaacademico:id/btnEnviar"]
    
    Wait Until Element Is Visible    xpath=//android.widget.ImageButton[@content-desc="Navigate up"]   10s
    Click Element    xpath=//android.widget.ImageButton[@content-desc="Navigate up"]
    Sleep    1
    
    Wait Until Element Is Visible    xpath=//android.widget.ImageButton[@content-desc="Navigate up"]   10s
    Click Element    xpath=//android.widget.ImageButton[@content-desc="Navigate up"]
    Sleep    1
    

    Wait Until Element Is Visible    xpath=//androidx.recyclerview.widget.RecyclerView[@resource-id="com.fied.guiaacademico:id/rvDisciplinas"]/androidx.cardview.widget.CardView[2]/android.widget.LinearLayout/android.widget.ImageView    10s
    Click Element    xpath=//androidx.recyclerview.widget.RecyclerView[@resource-id="com.fied.guiaacademico:id/rvDisciplinas"]/androidx.cardview.widget.CardView[2]/android.widget.LinearLayout/android.widget.ImageView
    Sleep    2

    Swipe    890    2184    885    986

    Capture Page Screenshot
    Close Application
HAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA