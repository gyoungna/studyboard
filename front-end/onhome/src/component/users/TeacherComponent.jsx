import React, {Component} from 'react';
import ApiService from "../../ApiService";
import axios from 'axios';
import TabforUser from "../users/TabforUser"; 
import TabforOmr from "../users/TabforOmr"; 

class TeacherComponent extends Component{

    state={
        user:'',
        banList:[],
        temp:''
        
     }//this.setState()할때마다 rerendering

     componentWillMount(){
        if(localStorage.getItem('auth')!='TEA'){
            alert("권한이 없습니다.");
            alert("로그아웃됩니다.");
            this.logout(null);
            
        }
    }

    componentDidMount(){
        this.setupAxiosInterceptors();
        ApiService.loadUser(localStorage.getItem('authenticatedUser'))
        .then(res=>{
            
            this.setState({
                user:res.data,
                banList:res.data.ban.split(';')
            })


        })
        .catch(err=>{
            console.log('load error');
        })

        
        
    }
     
     setupAxiosInterceptors(){
         axios.interceptors.request.use(
             config=>{
                 const token=window.localStorage.getItem('token');
                 if(token){
                     config.headers['Authorization']='Bearer '+ token;
                 }
                 return config;
             },
             
         )
 
     }// 이후의 모든 request에 token이 담겨져서 보내짐//모든 request를 보내기전에 intecept, 그래서  localstorge를 삭제하면 아무것도 보내지지 않음
 
   
     
     logout=(e)=>{
         e.preventDefault();//a태그의 href사용하지말고 onClick을 이용하도록 하는 것 
         localStorage.removeItem('token');
         localStorage.removeItem('authenticatedUser');
         localStorage.removeItem('auth');
 
         this.props.history.push('/main');
     }
 
     
     editUser=(e)=>{//회원 정보 수정
         e.preventDefault();//a태그의 href사용하지말고 onClick을 이용하도록 하는 것 
 
         this.props.history.push('/edit');
     }
 
     
     createBan=(e)=>{//반 추가생성
         e.preventDefault();
        let length;
        let temp;
        if(this.state.banList==null){
            temp=this.state.temp;
        }
        else{
            temp=this.state.user.ban+';'+this.state.temp;
        }

         this.setState({
            user:{
                ...this.state.user,
                ban:temp,

            }
         }, ()=>{
             ApiService.updateUser(this.state.user)
         .then(res=>{
             console.log('success');
             document.location.href="/teacher";
         })
         .catch(err=>{
             console.log('update ban fail');
         })
        })
         
        

     }
  
 
   
     render(){
        
         const hStyle={
             position:"absolute",
             width:"800px",
             height:"100px",
             top:"10%",
             left:"50%",
             transform:"translate(-50%,-50%)",
 
         }

         const mStyle={
            position:"absolute",
            width:"500px",
            height:"300px",
            top:"20%",
            left:"30%",



        }

         return(
    <div>
     <div style={hStyle}>
     <h1 style={{ padding:"20px",textAlign:"center"}}>
     onHome
     </h1 >
     <ul class="nav nav-pills" style={{paddingBottom:"5px"}}>
   <li class="nav-item" ><a class="nav-link active" href="#" onClick={(e)=>{e.preventDefault(); this.props.history.push('/teacher');}} >선생님 페이지</a></li>
   <div style={{position:"absolute", right:"10px"}}>
   <li style={{display:"inline-block"}}><small class="form-text text-muted " style={{position:"relative", paddingTop:"3px"}}>안녕하세요 {this.state.user.id}선생님!!</small></li>
   <li class="nav-item" style={{display:"inline-block"}}><a class="nav-link"  href="#" onClick={this.editUser}>회원 정보 수정</a></li>
   <li class="nav-item" style={{display:"inline-block"}}><a class="nav-link" href="#" onClick={this.logout}>로그아웃</a></li>
 </div>
 </ul>
 <ul class="nav nav-tabs" role="tablist" style={{borderTop:"3px solid #56cc9d",paddingTop:"25px"}}>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">숙제관리</a>
    <div class="dropdown-menu" >
    <>
    {this.state.banList&&this.state.banList.map(ban=>
        {
       return  <a class="dropdown-item"  data-toggle="tab" href={"#ban"+ban}>{ban}</a>;
       }
    )
     }
    
    </>
      <div class="dropdown-divider"></div>
      <a class="dropdown-item" data-toggle="modal" data-target="#myModal">반추가</a>
    </div>
  </li>
  <li class="nav-item">
    <a class="nav-link active" data-toggle="tab" href="#control" style={{color:"#5a5a5a"}}>학생 관리</a>
  </li>
</ul>
<div id="myTabContent" class="tab-content">
  <div class="tab-pane fade show active" id="control">
            <p><TabforUser user={this.state.user}></TabforUser></p>
 </div>
 {this.state.banList&&this.state.banList.map(ban=>
        
        <div class="tab-pane fade" id={"ban"+ban}>
        <p><TabforOmr ban={ban} user={this.state.user} history={this.props.history}></TabforOmr></p>
        </div>
)}

</div>
 </div>

 <div class="modal fade" id="myModal" style={mStyle} role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                        <h4 class="modal-title">반 추가</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        <label style={{display:"inline-block"}}>반 이름:</label>&nbsp;
                        <input style={{display:"inline-block", width:"100px"}} type="text" class="form-control" name="ban" value={this.state.temp} onChange={(e)=>{this.setState({temp:e.target.value});}}/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onClick={(e)=>{this.createBan(e);}}>추가</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        </div>
                    </div>
                </div>
    </div>

 </div>
         );
     }
}
export default TeacherComponent;