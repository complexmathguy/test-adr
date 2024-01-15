import React, { Component } from 'react'
import ReportDescriptorService from '../services/ReportDescriptorService';

class CreateReportDescriptorComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
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

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
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
    }
    saveOrUpdateReportDescriptor = (e) => {
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

        // step 5
        if(this.state.id === '_add'){
            reportDescriptor.reportDescriptorId=''
            ReportDescriptorService.createReportDescriptor(reportDescriptor).then(res =>{
                this.props.history.push('/reportDescriptors');
            });
        }else{
            ReportDescriptorService.updateReportDescriptor(reportDescriptor).then( res => {
                this.props.history.push('/reportDescriptors');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ReportDescriptor</h3>
        }else{
            return <h3 className="text-center">Update ReportDescriptor</h3>
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
                                            <label> payloadType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> readingType: </label>
                                            #formFields( $attribute, 'create')
                                            <label> units: </label>
                                            #formFields( $attribute, 'create')
                                            <label> aggregate: </label>
                                            #formFields( $attribute, 'create')
                                            <label> startInterval: </label>
                                            #formFields( $attribute, 'create')
                                            <label> numIntervals: </label>
                                            #formFields( $attribute, 'create')
                                            <label> historical: </label>
                                            #formFields( $attribute, 'create')
                                            <label> frequency: </label>
                                            #formFields( $attribute, 'create')
                                            <label> repeat: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateReportDescriptor}>Save</button>
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

export default CreateReportDescriptorComponent
