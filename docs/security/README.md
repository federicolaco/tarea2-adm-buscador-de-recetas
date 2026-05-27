# Informes de seguridad y calidad

El proyecto tiene configurado **Detekt** (análisis estático del código Kotlin).

## Informe SAST con Detekt

```bash
gradlew.bat :app:detekt
```

**Resultado:** `docs/security/detekt-report.html`

Abrí el HTML en el navegador y revisá los hallazgos. Para la entrega conviene documentar los que queden y por qué se aceptan.

## Qué entregar

```
docs/security/
└── detekt-report.html
```

## Resumen para la defensa (PPT)

- **SAST:** Detekt sobre código Kotlin → `detekt-report.html`
- **Conclusión:** resumir cantidad y severidad de hallazgos (mayormente estilo/calidad de código)
