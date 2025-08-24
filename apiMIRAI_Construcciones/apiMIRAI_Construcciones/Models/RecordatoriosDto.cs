using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using APIMIRAI_Construcciones.Data;
using System.Web.Http.Description;
using System.Web.Http;

namespace APIMIRAI_Construcciones.Models
{
    public class RecordatoriosDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idRecordatorios { get; set; }
        public Nullable<int> idfEquipos { get; set; }
        public Nullable<int> idfTareas { get; set; }
        public Nullable<int> idfUsuarios { get; set; }
        public Nullable<int> idfPrioridades { get; set; }
        public Nullable<int> idfTiposMantenimientos { get; set; }
        public byte[] recordarS_N { get; set; }
        public Nullable<System.DateTime> fechaRecordatorio { get; set; }
        public Nullable<int> numeroReporte { get; set; }
        public string descripcion { get; set; }

        public EquiposDto EquiposDto { get; set; }
        public PrioridadesDto PrioridadesDto { get; set; }
        public TareasDto TareasDto { get; set; }
        public TiposMantenimientosDto TiposMantenimientosDto { get; set; }
        public UsuariosDto UsuariosDto { get; set; }
    }
}