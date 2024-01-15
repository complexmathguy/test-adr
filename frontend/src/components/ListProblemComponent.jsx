import React, { Component } from 'react'
import ProblemService from '../services/ProblemService'

class ListProblemComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                problems: []
        }
        this.addProblem = this.addProblem.bind(this);
        this.editProblem = this.editProblem.bind(this);
        this.deleteProblem = this.deleteProblem.bind(this);
    }

    deleteProblem(id){
        ProblemService.deleteProblem(id).then( res => {
            this.setState({problems: this.state.problems.filter(problem => problem.problemId !== id)});
        });
    }
    viewProblem(id){
        this.props.history.push(`/view-problem/${id}`);
    }
    editProblem(id){
        this.props.history.push(`/add-problem/${id}`);
    }

    componentDidMount(){
        ProblemService.getProblems().then((res) => {
            this.setState({ problems: res.data});
        });
    }

    addProblem(){
        this.props.history.push('/add-problem/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Problem List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addProblem}> Add Problem</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Type </th>
                                    <th> Title </th>
                                    <th> Status </th>
                                    <th> Detail </th>
                                    <th> Instance </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.problems.map(
                                        problem => 
                                        <tr key = {problem.problemId}>
                                             <td> { problem.type } </td>
                                             <td> { problem.title } </td>
                                             <td> { problem.status } </td>
                                             <td> { problem.detail } </td>
                                             <td> { problem.instance } </td>
                                             <td>
                                                 <button onClick={ () => this.editProblem(problem.problemId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteProblem(problem.problemId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewProblem(problem.problemId)} className="btn btn-info btn-sm">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListProblemComponent
