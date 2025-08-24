using APIMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class UsuariosDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idUsuarios { get; set; }
        public string nombre { get; set; }
        public string apellidoPaterno { get; set; }
        public string apellidoMaterno { get; set; }
        public string contraseña { get; set; }
        public Nullable<int> idfRoles { get; set; }
        public string telefono { get; set; }

        [JsonIgnore]
        public ICollection<Recordatorios> RecordatoriosDto { get; set; }
        [JsonIgnore]
        public ICollection<Revisiones> RevisionesDto { get; set; }
        public Roles RolesDto { get; set; }
    }
}