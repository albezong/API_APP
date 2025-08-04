using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class RecordatoriosDto
	{
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