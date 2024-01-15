import React, { Component } from 'react'
import ReportDescriptorService from '../services/ReportDescriptorService';

class UpdateReportDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                payloadType: '',
                readingType: '',
                units: '',
                aggregate: '',
                startInterval: '',
                numIntervals: '',
                historical: '',
                frequency: '',
                repeat: ''
        }
        this.updateReportDescriptor = this.updateReportDescriptor.bind(this);

        this.changepayloadTypeHandler = this.changepayloadTypeHandler.bind(this);
        this.changereadingTypeHandler = this.changereadingTypeHandler.bind(this);
        this.changeunitsHandler = this.changeunitsHandler.bind(this);
        this.changeaggregateHandler = this.changeaggregateHandler.bind(this);
        this.changestartIntervalHandler = this.changestartIntervalHandler.bind(this);
        this.changenumIntervalsHandler = this.changenumIntervalsHandler.bind(this);
        this.changehistoricalHandler = this.changehistoricalHandler.bind(this);
        this.changefrequencyHandler = this.changefrequencyHandler.bind(this);
        this.changerepeatHandler = this.changerepeatHandler.bind(this);
    }

    componentDidMount(){
        ReportDescriptorService.getReportDescriptorById(this.state.id).then( (res) =>{
            let reportDescriptor = res.data;
            this.setState({
                payloadType: reportDescriptor.payloadType,
                readingType: reportDescriptor.readingType,
                units: reportDescriptor.units,
                aggregate: reportDescriptor.aggregate,
                startInterval: reportDescriptor.startInterval,
                numIntervals: reportDescriptor.numIntervals,
                historical: reportDescriptor.historical,
                frequency: reportDescriptor.frequency,
                repeat: reportDescriptor.repeat
            });
        });
    }

    updateReportDescriptor = (e) => {
        e.preventDefault();
        let reportDescriptor = {
            reportDescriptorId: this.state.id,
            payloadType: this.state.payloadType,
            readingType: this.state.readingType,
            units: this.state.units,
            aggregate: this.state.aggregate,
            startInterval: this.state.startInterval,
            numIntervals: this.state.numIntervals,
            historical: this.state.historical,
            frequency: this.state.frequency,
            repeat: this.state.repeat
        };
        console.log('reportDescriptor => ' + JSON.stringify(reportDescriptor));
        console.log('id => ' + JSON.stringify(this.state.id));
        ReportDescriptorService.updateReportDescriptor(reportDescriptor).then( res => {
            this.props.history.push('/reportDescriptors');
        });
    }

    changepayloadTypeHandler= (event) => {
        this.setState({payloadType: event.target.value});
    }
    changereadingTypeHandler= (event) => {
        this.setState({readingType: event.target.value});
    }
    changeunitsHandler= (event) => {
        this.setState({units: event.target.value});
    }
    changeaggregateHandler= (event) => {
        this.setState({aggregate: event.target.value});
    }
    changestartIntervalHandler= (event) => {
        this.setState({startInterval: event.target.value});
    }
    changenumIntervalsHandler= (event) => {
        this.setState({numIntervals: event.target.value});
    }
    changehistoricalHandler= (event) => {
        this.setState({historical: event.target.value});
    }
    changefrequencyHandler= (event) => {
        this.setState({frequency: event.target.value});
    }
    changerepeatHandler= (event) => {
        this.setState({repeat: event.target.value});
    }

    cancel(){
        this.props.history.push('/reportDescriptors');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update ReportDescriptor</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> payloadType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> readingType: </label>
                                            #formFields( $attribute, 'update')
                                            <label> units: </label>
                                            #formFields( $attribute, 'update')
                                            <label> aggregate: </label>
                                            #formFields( $attribute, 'update')
                                            <label> startInterval: </label>
                                            #formFields( $attribute, 'update')
                                            <label> numIntervals: </label>
                                            #formFields( $attribute, 'update')
                                            <label> historical: </label>
                                            #formFields( $attribute, 'update')
                                            <label> frequency: </label>
                                            #formFields( $attribute, 'update')
                                            <label> repeat: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateReportDescriptor}>Save</button>
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

export default UpdateReportDescriptorComponent
