import React, { Component } from 'react'
import ReportPayloadDescriptorService from '../services/ReportPayloadDescriptorService'

class ViewReportPayloadDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            reportPayloadDescriptor: {}
        }
    }

    componentDidMount(){
        ReportPayloadDescriptorService.getReportPayloadDescriptorById(this.state.id).then( res => {
            this.setState({reportPayloadDescriptor: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View ReportPayloadDescriptor Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> payloadType:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.payloadType }</div>
                        </div>
                        <div className = "row">
                            <label> readingType:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.readingType }</div>
                        </div>
                        <div className = "row">
                            <label> units:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.units }</div>
                        </div>
                        <div className = "row">
                            <label> accuracy:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.accuracy }</div>
                        </div>
                        <div className = "row">
                            <label> confidence:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.confidence }</div>
                        </div>
                        <div className = "row">
                            <label> ObjectType:&emsp; </label>
                            <div> { this.state.reportPayloadDescriptor.objectType }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewReportPayloadDescriptorComponent
