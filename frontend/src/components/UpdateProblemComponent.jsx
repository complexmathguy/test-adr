import React, { Component } from 'react'
import ProblemService from '../services/ProblemService';

class UpdateProblemComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                type: '',
                title: '',
                status: '',
                detail: '',
                instance: ''
        }
        this.updateProblem = this.updateProblem.bind(this);

        this.changetypeHandler = this.changetypeHandler.bind(this);
        this.changetitleHandler = this.changetitleHandler.bind(this);
        this.changestatusHandler = this.changestatusHandler.bind(this);
        this.changedetailHandler = this.changedetailHandler.bind(this);
        this.changeinstanceHandler = this.changeinstanceHandler.bind(this);
    }

    componentDidMount(){
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

    updateProblem = (e) => {
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
        console.log('id => ' + JSON.stringify(this.state.id));
        ProblemService.updateProblem(problem).then( res => {
            this.props.history.push('/problems');
        });
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

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Problem</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> type: </label>
                                            #formFields( $attribute, 'update')
                                            <label> title: </label>
                                            #formFields( $attribute, 'update')
                                            <label> status: </label>
                                            #formFields( $attribute, 'update')
                                            <label> detail: </label>
                                            #formFields( $attribute, 'update')
                                            <label> instance: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateProblem}>Save</button>
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

export default UpdateProblemComponent
