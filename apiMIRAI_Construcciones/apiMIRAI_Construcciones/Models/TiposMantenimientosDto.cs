using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class TiposMantenimientosDto
	{
        public int idTiposMantenimientos { get; set; }
        public string nombre { get; set; }

        public ICollection<RecordatoriosDto> RecordatoriosDto { get; set; }
        public ICollection<RevisionesDto> RevisionesDto { get; set; }
        public ICollection<TareasDto> TareasDto { get; set; }
    }
}