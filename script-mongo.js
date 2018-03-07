conn = new Mongo();
db = connect("localhost:27017/cyberholocampus");
db.batiments.drop();
//db.dropDatabase();

db.buildings.insertMany(
  [
    {
      name : "Polytech Grenoble",
      type : "",
      position : {
        lat : 45.184470,
        lon : 5.752970
      },
      info : "Lieu de travail des étudiants de Polytech.",
      anchors : [1,2,4,8,16,32]
    },
    {
      name : "Bibliothèque Universitaire",
      type : "",
      position : {
        lat : 45.201920,
        lon : 5.744874
      },
      info : "Bibliothèque des sciences"
    },
    {
      name : "Barnave",
      type : "restaurant",
      position : {
        lat : 45.190651,
        lon : 5.760875
      },
      info : "Feu restaurant préféré des étudiants. RIP.",
      anchors : [15,45,65,98,15,21,32,15,48]
    }
  ]
);

res = db.batiments.find();
while (res.hasNext()) {
   printjson(res.next());
}
