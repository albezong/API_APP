using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Mvc;
using apiMIRAI_Construcciones.Data;
using apiMIRAI_Construcciones.Helper;
using iTextSharp.text.pdf;
using iTextSharp.text;
using System.Runtime.Remoting.Contexts;
using apiMIRAI_Construcciones.Infraestructura;
using apiMIRAI_Construcciones.Services;

namespace apiMIRAI_Construcciones.Controllers
{
    public class QrEquiposController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();
        private readonly IUnitOfWork _unitOfWork;

        public QrEquiposController()
        {
            _unitOfWork = new UnitOfWork();
        }

        // GET: api/QrEquipos
        public IQueryable<QrEquipos> GetQrEquipos()
        {
            return db.QrEquipos;
        }

        // GET: api/QrEquipos/5
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult GetQrEquipos(int id)
        {
            QrEquipos qrEquipos = db.QrEquipos.Find(id);
            if (qrEquipos == null)
            {
                return NotFound();
            }

            return Ok(qrEquipos);
        }

        // PUT: api/QrEquipos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutQrEquipos(int id, QrEquipos qrEquipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != qrEquipos.idQrEquipos)
            {
                return BadRequest();
            }

            db.Entry(qrEquipos).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!QrEquiposExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/QrEquipos
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult PostQrEquipos(QrEquipos qrEquipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.QrEquipos.Add(qrEquipos);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = qrEquipos.idQrEquipos }, qrEquipos);
        }

        // DELETE: api/QrEquipos/5
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult DeleteQrEquipos(int id)
        {
            QrEquipos qrEquipos = db.QrEquipos.Find(id);
            if (qrEquipos == null)
            {
                return NotFound();
            }

            db.QrEquipos.Remove(qrEquipos);
            db.SaveChanges();

            return Ok(qrEquipos);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool QrEquiposExists(int id)
        {
            return db.QrEquipos.Count(e => e.idQrEquipos == id) > 0;
        }



        //--------------------------*****************************

        // GenerarQR: api/QrEquipos/5
        public FileResult GenerarQR(int id)
        {
            // Obtener un solo equipo por id
            var equipo = db.QrEquipos
                           .Include(q => q.Equipos) // si tienes relación con la tabla Equipos
                           .FirstOrDefault(p => p.idQrEquipos == id);

            if (equipo == null) return null;

            // Aquí colocas los datos que quieres que aparezcan en el QR
            var datos1 = $"{equipo.Equipos.codigoArticulo}, " +
                $"{equipo.Equipos.nombreArticulo}, " +
                $"{equipo.Equipos.nombreComercial}, " +
                $"{equipo.Equipos.numIdentificador}, " +
                $"{equipo.Equipos.descripcion}, " +
                $"{equipo.Equipos.marca}, " +
                $"{equipo.Equipos.modelo}, " +
                $"{equipo.Equipos.idfEstatus}, " +
                $"{equipo.Equipos.idfTiposMaquinarias}";

            // Generar QR con esos datos
            var qrBytes = QrCodeGeneratorHelper.GenerateQRCode(datos1);

            if (qrBytes != null)
            {
                _unitOfWork.GuardarCambios(id, qrBytes);
            }

            return new FileContentResult(qrBytes, "image/png");
        }

        public FileResult VerQR(int id)
        {
            var persona = db.QrEquipos.GetById(id);
            if (persona == null) return null;

            // Validación del contenido
            if (string.IsNullOrWhiteSpace(persona.ProviderKey)) return null;

            byte[] qrBytes;

            try
            {
                qrBytes = Convert.FromBase64String(persona.ProviderKey);
            }
            catch
            {
                // Base64 inválido
                return null;
            }

            return File(qrBytes, "image/png");
        }

        /*public FileResult VerQR(int id)
        {
            var persona = _unitOfWork.Usuarios.GetById(id);
            if(persona == null) return null;

            var QR = persona.ProviderKey;


            //var datos = $"{persona.Nombres} {persona.Apellidos} {persona.Email}";

            //var qrBytes = Helper.QrCodeGeneratorHelper.GenerateQRCode(datos); //es una imagen
            //ViewBag.QRCodeImage = Convert.ToBase64String(qrBytes); //en un string convierte qrBytes

            //var qrImage = QR;
            //var qrImage = StringConverter.StringToByteArray(QR);

            //return File(StringConverter.StringToByteArray(QR), "image/png");
            //return File(StringConverter.StringToByteArray(QR), "image/jpg");


            //ImagenDesdeSQL.MostrarImagen(QR, persona.Id, pictureBox1);

            byte[] qrBytes = StringConverter.StringToByteArray(persona.ProviderKey); // si ya lo tienes como Base64
            string base64Image = Convert.ToBase64String(qrBytes);

            //ViewBag.QRImage = base64Image;
            //return View(persona);



            return File(qrBytes, "image/png", "qrCode.png");


            /*var persona = _unitOfWork.Usuarios.GetById(id);
            if(persona == null) return HttpNotFound();
            var datos = $"{persona.Nombres} {persona.Apellidos} {persona.Email}";

            var qrBytes = Helper.QrCodeGeneratorHelper.GenerateQRCode(datos); //es una imagen
            ViewBag.QRCodeImage = Convert.ToBase64String(qrBytes); //en un string convierte qrBytes
            return View(persona);*
        }*/

        public FileResult DescargarPDF(int id)
        {
            var persona = db.QrEquipos.GetById(id);
            //if(persona is null) return null;
            if (persona is null)
            {
                return null;
            }
            var datos = $"{persona.Nombres} {persona.Apellidos} {persona.Email}";
            var qrBytes = Helper.QrCodeGeneratorHelper.GenerateQRCode(datos);

            using (var es = new MemoryStream())
            {
                var document = new Document();
                PdfWriter.GetInstance(document, es);
                document.Open();
                var qrImage = Image.GetInstance(qrBytes);
                qrImage.ScaleToFit(200f, 200f);
                document.Add(new Paragraph("Codigo QR para: "));
                document.Add(new Paragraph($"{persona.Nombres} {persona.Apellidos} {persona.Email}"));
                document.Add(qrImage);
                document.Close();
                return File(es.ToArray(),
                    "aplication/pdf", $"QR_{persona.Nombres}.pdf"
                    );
            }

        }
    }
}