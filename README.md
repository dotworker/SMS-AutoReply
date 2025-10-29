## Über diese App

Diese Android-App wurde entwickelt, um ein häufiges Problem zu lösen: Das Datenvolumen geht während wichtiger Meetings aus, und man muss manuell zum Handy greifen, um per SMS neues Volumen nachzubuchen - meist in kleinen 2GB-Schritten. Oft bei on Demand Tarifen.

Da im Play Store keine passende Lösung zu finden war (entweder nicht geeignet oder zu teuer), habe ich selbst eine App programmiert. Dies ist meine erste Android-App, daher gibt es sicherlich noch Verbesserungspotenzial. Aber sie funktioniert zuverlässig und läuft stabil (zumindest auf meinem Gerät ;-) ).

## Installation

1. Laden Sie die APK-Datei herunter
2. Installieren Sie die App auf Ihrem Android-Gerät
3. Bestätigen Sie nach der Installation die erforderlichen Berechtigungen (SMS-Zugriff)

## Benutzeroberfläche

Nach dem Start sehen Sie folgende Oberfläche:

<img width="429" height="887" alt="image" src="https://github.com/user-attachments/assets/fcb95eae-f36b-4c58-bad4-bd395d9c87da" />

### Bedienelemente im Detail

<img width="429" height="887" alt="image" src="https://github.com/user-attachments/assets/c3ea0335-b7f4-4bcf-8dd8-7d485e53c1fc" />

1. **Aktivierungsschalter**: Hier können Sie die automatische SMS-Antwort aktivieren oder deaktivieren.

2. **Zufällige Verzögerung**: Wenn aktiviert, wird die Antwort-SMS nach einer zufälligen Verzögerung zwischen 0 und 10 Sekunden versendet. Dies sorgt für ein natürlicheres Verhalten.

3. **Schlüsselwärter**: Geben Sie hier die Schlüsselwörter ein, nach denen in eingehenden SMS gesucht werden soll (z.B. "verbraucht Datenvolumen"). Dies ist wichtig, damit die App nicht auf jede SMS von der konfigurierten Nummer mit demselben Text antwortet.

4. **Absender-Telefonnummer**: Tragen Sie hier die Telefonnummer(n) ein, auf die die App reagieren soll (z.B. "01234"). Mehrere Nummern können durch Kommas getrennt eingegeben werden (z.B. "01234,56789").

5. **Antwort-Text**: Der Text, der automatisch als Antwort gesendet werden soll (z.B. "VOLUMEN"). In der Regel steht in der SMS Ihres Mobilfunkanbieters, welches Schlüsselwort für die Nachbuchung verwendet werden muss.

6. **Speichern-Button**: Speichert Ihre Einstellungen.

## Funktionsweise

Die App Überwacht eingehende SMS-Nachrichten. Wenn eine SMS von einer konfigurierten Telefonnummer empfangen wird und die angegebenen Schlüsselwörter enthält, sendet die App automatisch den definierten Antwort-Text zurück. Durch die optionale Verzögerung wirkt die Antwort natürlicher und nicht wie eine automatisierte Reaktion.

## Hinweise

- Stellen Sie sicher, dass die App die erforderlichen Berechtigungen für SMS-Empfang und -Versand hat
- Die App läuft im Hintergrund und Überwacht eingehende Nachrichten kontinuierlich
- Überprüfen Sie die Einstellungen regelmäßig, um sicherzustellen, dass sie Ihren aktuellen Anforderungen entsprechen

---

**Hinweis**: Dies ist die erste Version dieser App. Feedback und Verbesserungsvorschläge sind willkommen!
