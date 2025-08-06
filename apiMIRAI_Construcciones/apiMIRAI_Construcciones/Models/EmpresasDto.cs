using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class EmpresasDto
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idEmpresas { get; set; }
        public string nombre { get; set; }

        public ICollection<RevisionesDto> RevisionesDto { get; set; }
    }
}