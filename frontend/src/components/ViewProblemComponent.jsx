import React, { Component } from 'react'
import ProblemService from '../services/ProblemService'

class ViewProblemComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            problem: {}
        }
    }

    componentDidMount(){
        ProblemService.getProblemById(this.state.id).then( res => {
            this.setState({problem: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Problem Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> type:&emsp; </label>
                            <div> { this.state.problem.type }</div>
                        </div>
                        <div className = "row">
                            <label> title:&emsp; </label>
                            <div> { this.state.problem.title }</div>
                        </div>
                        <div className = "row">
                            <label> status:&emsp; </label>
                            <div> { this.state.problem.status }</div>
                        </div>
                        <div className = "row">
                            <label> detail:&emsp; </label>
                            <div> { this.state.problem.detail }</div>
                        </div>
                        <div className = "row">
                            <label> instance:&emsp; </label>
                            <div> { this.state.problem.instance }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewProblemComponent
