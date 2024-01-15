import React, { Component } from 'react'
import ProblemService from '../services/ProblemService';

class CreateProblemComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                type: '',
                title: '',
                status: '',
                detail: '',
                instance: ''
        }
        this.changetypeHandler = this.changetypeHandler.bind(this);
        this.changetitleHandler = this.changetitleHandler.bind(this);
        this.changestatusHandler = this.changestatusHandler.bind(this);
        this.changedetailHandler = this.changedetailHandler.bind(this);
        this.changeinstanceHandler = this.changeinstanceHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            ProblemService.getProblemById(this.state.id).then( (res) =>{
                let problem = res.data;
                this.setState({
                    type: problem.type,
                    title: problem.title,
                    status: problem.status,
                    detail: problem.detail,
                    instance: problem.instance
                });
            });
        }        
    }
    saveOrUpdateProblem = (e) => {
        e.preventDefault();
        let problem = {
                problemId: this.state.id,
                type: this.state.type,
                title: this.state.title,
                status: this.state.status,
                detail: this.state.detail,
                instance: this.state.instance
            };
        console.log('problem => ' + JSON.stringify(problem));

        // step 5
        if(this.state.id === '_add'){
            problem.problemId=''
            ProblemService.createProblem(problem).then(res =>{
                this.props.history.push('/problems');
            });
        }else{
            ProblemService.updateProblem(problem).then( res => {
                this.props.history.push('/problems');
            });
        }
    }
    
    changetypeHandler= (event) => {
        this.setState({type: event.target.value});
    }
    changetitleHandler= (event) => {
        this.setState({title: event.target.value});
    }
    changestatusHandler= (event) => {
        this.setState({status: event.target.value});
    }
    changedetailHandler= (event) => {
        this.setState({detail: event.target.value});
    }
    changeinstanceHandler= (event) => {
        this.setState({instance: event.target.value});
    }

    cancel(){
        this.props.history.push('/problems');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Problem</h3>
        }else{
            return <h3 className="text-center">Update Problem</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> type: </label>
                                            #formFields( $attribute, 'create')
                                            <label> title: </label>
                                            #formFields( $attribute, 'create')
                                            <label> status: </label>
                                            #formFields( $attribute, 'create')
                                            <label> detail: </label>
                                            #formFields( $attribute, 'create')
                                            <label> instance: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateProblem}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                   </div>
            </div>
        )
    }
}

export default CreateProblemComponent
