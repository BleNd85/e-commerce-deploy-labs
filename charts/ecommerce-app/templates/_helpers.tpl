{{- /*
Expand the name of the chart.
*/}}
{{- define "ecommerce-app.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- /*
Create a default fully qualified app name.
Truncated at 63 chars because some Kubernetes name fields are limited to this.
If release name contains chart name it will be used as a full name.
*/}}
{{- define "ecommerce-app.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{- /*
Create chart name and version as used by the chart label.
*/}}
{{- define "ecommerce-app.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- /*
Common labels
*/}}
{{- define "ecommerce-app.labels" -}}
helm.sh/chart: {{ include "ecommerce-app.chart" . }}
app.kubernetes.io/name: {{ include "ecommerce-app.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{- /*
Selector labels
*/}}
{{- define "ecommerce-app.selectorLabels" -}}
app.kubernetes.io/name: {{ include "ecommerce-app.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- /*
DB name
*/}}
{{- define "ecommerce-app.db.name" -}}
{{- default "db" .Values.dbNameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- /*
DB fully qualified name
*/}}
{{- define "ecommerce-app.db.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "ecommerce-app.db.name" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- /*
DB common labels
*/}}
{{- define "ecommerce-app.db.labels" -}}
helm.sh/chart: {{ include "ecommerce-app.chart" . }}
app.kubernetes.io/name: {{ include "ecommerce-app.db.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/component: database
app.kubernetes.io/part-of: {{ include "ecommerce-app.name" . }}
{{- end }}

{{- /*
DB selector labels
*/}}
{{- define "ecommerce-app.db.selectorLabels" -}}
app.kubernetes.io/name: {{ include "ecommerce-app.db.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}