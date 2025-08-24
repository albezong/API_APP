using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Helper;
using APIMIRAI_Construcciones.Infraestructura;
using APIMIRAI_Construcciones.Models;
using APIMIRAI_Construcciones.Services;
using iTextSharp.text.pdf;
using iTextSharp.text;
using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using iTextSharp.text.pdf.draw;
using System.Windows.Controls;

namespace APIMIRAI_Construcciones.Controllers
{
    public class PDFController : ApiController
    {
        private readonly PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();


        // DESCARGAR PDF DescargarPdf_RevisionMantenimientoGETid_PREVENTIVOS
        [HttpGet]
        [Route("api/pdf_RevisionMantenimientoGETid_PREVENTIVOS/{id}")]
        public IHttpActionResult DescargarPdf_RevisionMantenimientoGETid_PREVENTIVOS(int id)
        {
            var revision = db.Revisiones
                .Include(r => r.Equipos)
                .Include(r => r.Empresas)
                .Include(r => r.Usuarios)
                .Include(r => r.DetallesPreventivos.Select(dp => dp.CategoriasPreventivas))
                .FirstOrDefault(r => r.idRevisiones == id);

            if (revision == null)
                return NotFound();

            using (var ms = new MemoryStream())
            {
                var doc = new Document(PageSize.A4, 40, 40, 40, 40);
                PdfWriter.GetInstance(doc, ms);
                doc.Open();

                var titulo = new Paragraph("Revisión programada para mantenimiento preventivo",
                    FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 14));
                titulo.Alignment = Element.ALIGN_CENTER;
                doc.Add(titulo);
                doc.Add(new Paragraph(" "));

                // === Descripción del equipo ===
                doc.Add(new Paragraph("Descripcion del equipo", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Equipo: {revision.Equipos.nombreArticulo}"));
                doc.Add(new Paragraph($"Marca: {revision.Equipos?.marca ?? ""}"));
                doc.Add(new Paragraph($"Modelo: {revision.Equipos?.modelo ?? ""}"));
                doc.Add(new Paragraph($"No. de serie: {revision.Equipos.numIdentificador}"));
                doc.Add(new Paragraph($"Descripción detallada: {revision.Equipos.descripcion}"));
                doc.Add(new Paragraph(" "));

                // === Empresa encargada ===
                doc.Add(new Paragraph("Empresa encargada del mantenimiento", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nombre: {revision.Empresas.nombre}"));
                doc.Add(new Paragraph($"Fecha de revisión programada: {revision.fecha:dd/MM/yyyy}"));
                var reportes = string.Join("", revision.DetallesPreventivos.Select(p => p.numeroReporte).Distinct()); // Evita duplicados si hay
                doc.Add(new Paragraph($"Numero de reporte: {reportes}"));
                doc.Add(new Paragraph(" "));

                // === Técnico encargado ===
                doc.Add(new Paragraph("Datos del técnico encargado de la revisión", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nombre: {revision.Usuarios.nombre} {revision.Usuarios.apellidoPaterno} {revision.Usuarios.apellidoMaterno}"));
                doc.Add(new Paragraph($"Teléfono: {revision.Usuarios.telefono}"));
                doc.Add(new Paragraph(" "));

                // === Tablas por categoría ===
                var categorias = revision.DetallesPreventivos.GroupBy(dp => dp.CategoriasPreventivas.nombreCategorias);
                foreach (var cat in categorias)
                {
                    var tableTitle = new PdfPCell(new Phrase(cat.Key, FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 11)));
                    tableTitle.Colspan = 4;
                    tableTitle.HorizontalAlignment = Element.ALIGN_CENTER;
                    tableTitle.BackgroundColor = BaseColor.LIGHT_GRAY;

                    var table = new PdfPTable(4);
                    table.WidthPercentage = 100;
                    table.AddCell(tableTitle);
                    table.AddCell("Parte");
                    table.AddCell("Estado");
                    table.AddCell("Comentarios");
                    table.AddCell("Observaciones");

                    foreach (var dp in cat)
                    {
                        table.AddCell(dp.nombreParte ?? "");
                        //table.AddCell(dp.EstadosPrioridad?.nombre ?? "");
                        table.AddCell(dp.Prioridades.nombre ?? "");
                        table.AddCell(dp.comentarios ?? "");
                        table.AddCell(dp.observaciones ?? "");
                    }

                    doc.Add(table);
                    doc.Add(new Paragraph(" "));
                }

                doc.Add(new Paragraph($"Descripción de lo que se realizó: {revision.DetallesPreventivos.FirstOrDefault(i=>i.idfRevisiones== id).comentarios}"));

                doc.Add(new Paragraph($"Conclusiones de lo que se realizó: {revision.DetallesPreventivos.FirstOrDefault(i=>i.idfRevisiones== id).observaciones}"));

                doc.Add(new Paragraph(" "));


                // === Referentes ===
                doc.Add(new Paragraph("Referentes la revisión", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph("Realizó la revisión preventiva"));
                doc.Add(new Paragraph($"Nombre: {revision.Usuarios.nombre} {revision.Usuarios.apellidoPaterno} {revision.Usuarios.apellidoMaterno}"));
                
                doc.Add(new Paragraph("Firma del responsable de la revisión:", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph(" "));
                doc.Add(new Paragraph(" "));
                var lineaFirma = new LineSeparator(1f, 100f, BaseColor.BLACK, Element.ALIGN_CENTER, -2);
                doc.Add(new Chunk(lineaFirma));

                doc.Close();

                var pdfBytes = ms.ToArray();
                return ResponseMessage(new HttpResponseMessage(HttpStatusCode.OK)
                {
                    Content = new ByteArrayContent(pdfBytes)
                    {
                        Headers =
                {
                    ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/pdf"),
                    ContentDisposition = new System.Net.Http.Headers.ContentDispositionHeaderValue("attachment")
                    {
                        FileName = $"MantenimientoPreventiva_{revision.Equipos.numIdentificador}.pdf"
                    }
                }
                    }
                });
            }
        }

        // DESCARGAR PDF DescargarPdf_RevisionMantenimientoGETid_CORRECTIVO 
        [HttpGet]
        [Route("api/pdf_RevisionMantenimientoGETid_CORRECTIVO/{id}")]
        public IHttpActionResult DescargarPdf_RevisionMantenimientoGETid_CORRECTIVO(int id)
        {
            var revision = db.Revisiones
                .Include(r => r.Equipos)
                .Include(r => r.Empresas)
                .Include(r => r.Usuarios)
                .Include(r => r.Refacciones)
                .FirstOrDefault(r => r.idRevisiones == id);

            if (revision == null)
                return NotFound();

            using (var ms = new MemoryStream())
            {
                var doc = new Document(PageSize.A4, 40, 40, 40, 40);
                PdfWriter.GetInstance(doc, ms);
                doc.Open();

                // === Título ===
                var titulo = new Paragraph("Reporte de mantenimiento correctivo/preventivo realizado",
                    FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 14));
                titulo.Alignment = Element.ALIGN_CENTER;
                doc.Add(titulo);
                doc.Add(new Paragraph(" "));

                // === Descripción del equipo ===
                doc.Add(new Paragraph("Descripcion del equipo", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Equipo: {revision.Equipos?.nombreArticulo ?? ""}"));
                doc.Add(new Paragraph($"Marca: {revision.Equipos?.marca ?? ""}"));
                doc.Add(new Paragraph($"Modelo: {revision.Equipos?.modelo ?? ""}"));
                doc.Add(new Paragraph(" "));

                // === Empresa encargada ===
                doc.Add(new Paragraph("Empresa encargada del mantenimiento", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nombre: {revision.Empresas?.nombre ?? ""}"));
                doc.Add(new Paragraph($"Fecha del mantto. correctivo: {revision.fecha:dd/MM/yyyy}"));

                var reportes = string.Join("", revision.Refacciones.Select(p => p.numeroReporte).Distinct()); // Evita duplicados si hay
                doc.Add(new Paragraph($"Numero de reporte: {reportes}"));

                doc.Add(new Paragraph($"Quien reporta/solicita: {revision.Empresas.reportaOsolicita ?? ""}"));
                doc.Add(new Paragraph(" "));

                // === Técnico encargado ===
                doc.Add(new Paragraph("Datos del técnico encargado del mantenimiento", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nombre: {revision.Usuarios?.nombre} {revision.Usuarios?.apellidoPaterno} {revision.Usuarios?.apellidoMaterno}"));
                doc.Add(new Paragraph($"Teléfono: {revision.Usuarios?.telefono ?? ""}"));
                //doc.Add(new Paragraph($"No. de identificación: {revision.Usuarios?.numIdentificacion ?? ""}"));
                doc.Add(new Paragraph(" "));

                // === Descripción del mantenimiento ===
                doc.Add(new Paragraph("Reparacion por descompostura/mantenimiento preventivo", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Descripcion del mantenimiento realizado: {revision.descripcion}"));
                doc.Add(new Paragraph(" "));

                // === Tabla de refacciones ===
                var tableTitle = new PdfPCell(new Phrase("Refacciones utilizadas", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 11)));
                tableTitle.Colspan = 5;
                tableTitle.HorizontalAlignment = Element.ALIGN_CENTER;
                tableTitle.BackgroundColor = BaseColor.LIGHT_GRAY;

                var table = new PdfPTable(5);
                table.WidthPercentage = 100;
                table.SetWidths(new float[] { 8, 30, 15, 15, 32 });
                table.AddCell(tableTitle);

                table.AddCell("No.");
                table.AddCell("Descripcion");
                table.AddCell("Cantidad");
                table.AddCell("Unidad");
                table.AddCell("Observaciones");

                int index = 1;
                foreach (var refa in revision.Refacciones)
                {
                    table.AddCell(index.ToString());
                    table.AddCell(refa.Prioridades.nombre?? "");
                    table.AddCell(refa.cantidad?.ToString() ?? "");
                    table.AddCell(refa.Unidades?.nombre ?? "");
                    table.AddCell(refa.observaciones ?? "");
                    index++;
                }

                doc.Add(table);

                doc.Close();

                var pdfBytes = ms.ToArray();
                return ResponseMessage(new HttpResponseMessage(HttpStatusCode.OK)
                {
                    Content = new ByteArrayContent(pdfBytes)
                    {
                        Headers =
                {
                    ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/pdf"),
                    ContentDisposition = new System.Net.Http.Headers.ContentDispositionHeaderValue("attachment")
                    {
                        FileName = $"MantenimientoCorrectivo_{revision.Equipos.numIdentificador}.pdf"
                    }
                }
                    }
                });
            }
        }



        // DESCARGAR PDF DescargarPdf_ProgramarRecordatoriosMantenimientoGETid
        [HttpGet]
        [Route("api/pdf_ProgramarRecordatoriosMantenimientoGETid/{id}")]
        public IHttpActionResult DescargarPdf_ProgramarRecordatoriosMantenimientoGETid(int id)
        {
            var recordatorio = db.Recordatorios
                .Include(r => r.Equipos)
                .Include(r => r.Tareas)
                .Include(r => r.Usuarios)
                .Include(r => r.Prioridades)
                .Include(r => r.TiposMantenimientos)
                .FirstOrDefault(r => r.idRecordatorios == id);

            if (recordatorio == null)
                return NotFound();

            using (var ms = new MemoryStream())
            {
                var doc = new Document(PageSize.A4, 40, 40, 40, 40);
                PdfWriter.GetInstance(doc, ms);
                doc.Open();

                // === Título ===
                var titulo = new Paragraph("Programar recordatorio",
                    FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 14));
                titulo.Alignment = Element.ALIGN_CENTER;
                doc.Add(titulo);
                doc.Add(new Paragraph(" "));

                // === Descripción del equipo ===
                doc.Add(new Paragraph("Descripcion del equipo", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Equipo: {recordatorio.Equipos?.nombreArticulo ?? ""}"));
                doc.Add(new Paragraph($"Marca: {recordatorio.Equipos?.marca ?? ""}"));
                doc.Add(new Paragraph($"Modelo: {recordatorio.Equipos?.modelo ?? ""}"));
                doc.Add(new Paragraph(" "));

                // === Tareas encargada ===
                doc.Add(new Paragraph("Tareas que se realizaran", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Tipo de mantenimeinto programado: {recordatorio.Tareas?.TiposMantenimientos.nombre ?? ""}"));
                doc.Add(new Paragraph($"Descripción del mantenimiento: {recordatorio.descripcion}"));
                doc.Add(new Paragraph($"Fecha programada: {recordatorio.fechaRecordatorio:dd/MM/yyyy}"));
                doc.Add(new Paragraph(" "));

                // === Técnico encargado ===
                doc.Add(new Paragraph("Datos del técnico encargado del mantenimiento", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nombre: {recordatorio.Usuarios?.nombre} {recordatorio.Usuarios?.apellidoPaterno} {recordatorio.Usuarios?.apellidoMaterno}"));
                doc.Add(new Paragraph($"Teléfono: {recordatorio.Usuarios?.telefono ?? ""}"));
                doc.Add(new Paragraph(" "));

                // === Recordatorio, prioridad ===
                doc.Add(new Paragraph("Recordatorio, prioridad", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));
                doc.Add(new Paragraph($"Nivel de prioridad: {recordatorio.Prioridades.nombre}"));
                doc.Add(new Paragraph(" "));


                doc.Close();

                var pdfBytes = ms.ToArray();
                return ResponseMessage(new HttpResponseMessage(HttpStatusCode.OK)
                {
                    Content = new ByteArrayContent(pdfBytes)
                    {
                        Headers =
                {
                    ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/pdf"),
                    ContentDisposition = new System.Net.Http.Headers.ContentDispositionHeaderValue("attachment")
                    {
                        FileName = $"Recordatorio_{recordatorio.Equipos.numIdentificador}.pdf"
                    }
                }
                    }
                });
            }
        }







        // DESCARGAR PDF Por Equipo GET id
        [HttpGet]
        [Route("api/pdf_porEquipoGETid/{id}")]
        public IHttpActionResult DescargarPDF_porEquipoGETid(int id)
        {
            var qrEquipo = db.QrEquipos
                .Include(q => q.Equipos)
                .FirstOrDefault(e => e.idQrEquipos == id);

            if (qrEquipo == null)
                return NotFound();

            if (string.IsNullOrWhiteSpace(qrEquipo.claveQR))
                return BadRequest("El QR no existe o no tiene datos.");

            // Convertir QR desde Base64
            byte[] qrBytes;
            try
            {
                qrBytes = Convert.FromBase64String(qrEquipo.claveQR);
            }
            catch
            {
                return BadRequest("El QR almacenado no es válido.");
            }

            using (var es = new MemoryStream())
            {
                var document = new Document(PageSize.A4);
                PdfWriter.GetInstance(document, es);
                document.Open();

                // Agregar imagen QR
                var qrImage = iTextSharp.text.Image.GetInstance(qrBytes);
                qrImage.ScaleToFit(200f, 200f);
                document.Add(qrImage);

                document.Add(new Paragraph(" "));

                // === Descripción del equipo ===
                document.Add(new Paragraph($"{qrEquipo.Equipos?.TiposMaquinarias.nombre}", FontFactory.GetFont(FontFactory.HELVETICA_BOLD, 12)));

                document.Add(new Paragraph($"Codigo De Articulo: {qrEquipo.Equipos?.codigoArticulo ?? ""}"));
                document.Add(new Paragraph($"Nombre De Articulo: {qrEquipo.Equipos?.nombreArticulo ?? ""}"));
                document.Add(new Paragraph($"Nombre Comercial: {qrEquipo.Equipos?.nombreComercial ?? ""}"));
                document.Add(new Paragraph($"Grupo al que pertenece: {qrEquipo.Equipos?.TiposMaquinarias.nombre ?? ""}"));
                document.Add(new Paragraph($"Codigo o numero identificador: {qrEquipo.Equipos.numIdentificador}"));
                document.Add(new Paragraph($"Marca: {qrEquipo.Equipos.marca}"));
                document.Add(new Paragraph($"Ubicación: {qrEquipo.Equipos.Ubicaciones.nombre}"));
                document.Add(new Paragraph($"Estatus de funcionamiento:  {qrEquipo.Equipos.Estatus.nombre}"));
                document.Add(new Paragraph($"Modelo: {qrEquipo.Equipos.modelo}"));
                document.Add(new Paragraph($"Descripcion: {qrEquipo.Equipos.descripcion}"));
                document.Add(new Paragraph($"Fecha de Registro: {qrEquipo.Equipos.fechadeRegistro:dd/MM/yyyy}"));
                //document.Add(new Paragraph($"Se encuentra en:{qrEquipo.Equipos.Lugares.Where(r => r.idfEquipos == qrEquipo.idEquipos).Select(y => y.nombreLugar).Distinct()}"));
                document.Add(new Paragraph($"Se encuentra en: {qrEquipo.Equipos.Lugares.FirstOrDefault(i => i.idfEquipos == id).nombreLugar}"));
                document.Add(new Paragraph(" "));

                document.Close();

                var pdfBytes = es.ToArray();

                // Devolver PDF con nombre correcto
                var result = new HttpResponseMessage(HttpStatusCode.OK)
                {
                    Content = new ByteArrayContent(pdfBytes)
                };
                result.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/pdf");
                result.Content.Headers.ContentDisposition = new System.Net.Http.Headers.ContentDispositionHeaderValue("attachment")
                {
                    FileName = $"Descripcion_Y_QR_{qrEquipo.Equipos.numIdentificador}_{qrEquipo.Equipos?.TiposMaquinarias.nombre ?? ""}.pdf" // nombre limpio
                };

                return ResponseMessage(result);
            }
        }


    }
}