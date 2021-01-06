console.clear();
const articleList = [];

const apiURL = 'article_list.json';

$.get(
    apiURL,
    {},
    function(data) {
        data.forEach((row, index) => {

        const article = {
        id:row.id,
        regDate:row.regDate,
        writer:row.extra_memberName,
        title:row.title,
        body:row.body
            
          };
          articleList.push(article);
        });
    },
    'json'
);

const articleListBox = new Vue({
  el:"#article-search-section",
  data:{
    articleList:articleList,
    keyword:''
  },
    methods:{
    source:_.debounce(function(event){
      this.keyword = event.target.value;
    }, 500)
  },
  computed:{
    filteredKeyword:function(){
      return this.keyword.toLowerCase();
    },
    filtered:function(){
      if(this.filteredKeyword.length == 0){
        return this.articleList;
      }
      
      return this.articleList.filter((row)=>{
        const keys = ['title', 'writer', 'body', 'regDate'];
        
        const match = keys.some((key)=>{
          if(row[key].toLowerCase().indexOf(this.filteredKeyword) > -1){
          return true;
        }
          
        });
        return match;
      });   
    }
  }
});
