import React, { Component } from 'react'
import ReportDescriptorService from '../services/ReportDescriptorService'

class ViewReportDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            reportDescriptor: {}
        }
    }

    componentDidMount(){
        ReportDescriptorService.getReportDescriptorById(this.state.id).then( res => {
            this.setState({reportDescriptor: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View ReportDescriptor Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> payloadType:&emsp; </label>
                            <div> { this.state.reportDescriptor.payloadType }</div>
                        </div>
                        <div className = "row">
                            <label> readingType:&emsp; </label>
                            <div> { this.state.reportDescriptor.readingType }</div>
                        </div>
                        <div className = "row">
                            <label> units:&emsp; </label>
                            <div> { this.state.reportDescriptor.units }</div>
                        </div>
                        <div className = "row">
                            <label> aggregate:&emsp; </label>
                            <div> { this.state.reportDescriptor.aggregate }</div>
                        </div>
                        <div className = "row">
                            <label> startInterval:&emsp; </label>
                            <div> { this.state.reportDescriptor.startInterval }</div>
                        </div>
                        <div className = "row">
                            <label> numIntervals:&emsp; </label>
                            <div> { this.state.reportDescriptor.numIntervals }</div>
                        </div>
                        <div className = "row">
                            <label> historical:&emsp; </label>
                            <div> { this.state.reportDescriptor.historical }</div>
                        </div>
                        <div className = "row">
                            <label> frequency:&emsp; </label>
                            <div> { this.state.reportDescriptor.frequency }</div>
                        </div>
                        <div className = "row">
                            <label> repeat:&emsp; </label>
                            <div> { this.state.reportDescriptor.repeat }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewReportDescriptorComponent
