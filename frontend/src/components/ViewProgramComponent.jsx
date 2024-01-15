import React, { Component } from 'react'
import ProgramService from '../services/ProgramService'

class ViewProgramComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            program: {}
        }
    }

    componentDidMount(){
        ProgramService.getProgramById(this.state.id).then( res => {
            this.setState({program: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Program Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> createdDateTime:&emsp; </label>
                            <div> { this.state.program.createdDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> modificationDateTime:&emsp; </label>
                            <div> { this.state.program.modificationDateTime }</div>
                        </div>
                        <div className = "row">
                            <label> programName:&emsp; </label>
                            <div> { this.state.program.programName }</div>
                        </div>
                        <div className = "row">
                            <label> programLongName:&emsp; </label>
                            <div> { this.state.program.programLongName }</div>
                        </div>
                        <div className = "row">
                            <label> retailerName:&emsp; </label>
                            <div> { this.state.program.retailerName }</div>
                        </div>
                        <div className = "row">
                            <label> retailerLongName:&emsp; </label>
                            <div> { this.state.program.retailerLongName }</div>
                        </div>
                        <div className = "row">
                            <label> programType:&emsp; </label>
                            <div> { this.state.program.programType }</div>
                        </div>
                        <div className = "row">
                            <label> country:&emsp; </label>
                            <div> { this.state.program.country }</div>
                        </div>
                        <div className = "row">
                            <label> principalSubdivision:&emsp; </label>
                            <div> { this.state.program.principalSubdivision }</div>
                        </div>
                        <div className = "row">
                            <label> timeZoneOffset:&emsp; </label>
                            <div> { this.state.program.timeZoneOffset }</div>
                        </div>
                        <div className = "row">
                            <label> programDescriptions:&emsp; </label>
                            <div> { this.state.program.programDescriptions }</div>
                        </div>
                        <div className = "row">
                            <label> bindingEvents:&emsp; </label>
                            <div> { this.state.program.bindingEvents }</div>
                        </div>
                        <div className = "row">
                            <label> localPrice:&emsp; </label>
                            <div> { this.state.program.localPrice }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.program.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewProgramComponent
