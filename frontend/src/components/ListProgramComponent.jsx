import React, { Component } from 'react'
import ProgramService from '../services/ProgramService'

class ListProgramComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                programs: []
        }
        this.addProgram = this.addProgram.bind(this);
        this.editProgram = this.editProgram.bind(this);
        this.deleteProgram = this.deleteProgram.bind(this);
    }

    deleteProgram(id){
        ProgramService.deleteProgram(id).then( res => {
            this.setState({programs: this.state.programs.filter(program => program.programId !== id)});
        });
    }
    viewProgram(id){
        this.props.history.push(`/view-program/${id}`);
    }
    editProgram(id){
        this.props.history.push(`/add-program/${id}`);
    }

    componentDidMount(){
        ProgramService.getPrograms().then((res) => {
            this.setState({ programs: res.data});
        });
    }

    addProgram(){
        this.props.history.push('/add-program/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Program List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addProgram}> Add Program</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> CreatedDateTime </th>
                                    <th> ModificationDateTime </th>
                                    <th> ProgramName </th>
                                    <th> ProgramLongName </th>
                                    <th> RetailerName </th>
                                    <th> RetailerLongName </th>
                                    <th> ProgramType </th>
                                    <th> Country </th>
                                    <th> PrincipalSubdivision </th>
                                    <th> TimeZoneOffset </th>
                                    <th> ProgramDescriptions </th>
                                    <th> BindingEvents </th>
                                    <th> LocalPrice </th>
                                    <th> ObjectType </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.programs.map(
                                        program => 
                                        <tr key = {program.programId}>
                                             <td> { program.createdDateTime } </td>
                                             <td> { program.modificationDateTime } </td>
                                             <td> { program.programName } </td>
                                             <td> { program.programLongName } </td>
                                             <td> { program.retailerName } </td>
                                             <td> { program.retailerLongName } </td>
                                             <td> { program.programType } </td>
                                             <td> { program.country } </td>
                                             <td> { program.principalSubdivision } </td>
                                             <td> { program.timeZoneOffset } </td>
                                             <td> { program.programDescriptions } </td>
                                             <td> { program.bindingEvents } </td>
                                             <td> { program.localPrice } </td>
                                             <td> { program.objectType } </td>
                                             <td>
                                                 <button onClick={ () => this.editProgram(program.programId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteProgram(program.programId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewProgram(program.programId)} className="btn btn-info btn-sm">View </button>
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

export default ListProgramComponent
