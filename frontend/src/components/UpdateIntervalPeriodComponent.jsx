import React, { Component } from 'react'
import IntervalPeriodService from '../services/IntervalPeriodService';

class UpdateIntervalPeriodComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                start: '',
                duration: '',
                randomizeStart: ''
        }
        this.updateIntervalPeriod = this.updateIntervalPeriod.bind(this);

        this.changestartHandler = this.changestartHandler.bind(this);
        this.changedurationHandler = this.changedurationHandler.bind(this);
        this.changerandomizeStartHandler = this.changerandomizeStartHandler.bind(this);
    }

    componentDidMount(){
        IntervalPeriodService.getIntervalPeriodById(this.state.id).then( (res) =>{
            let intervalPeriod = res.data;
            this.setState({
                start: intervalPeriod.start,
                duration: intervalPeriod.duration,
                randomizeStart: intervalPeriod.randomizeStart
            });
        });
    }

    updateIntervalPeriod = (e) => {
        e.preventDefault();
        let intervalPeriod = {
            intervalPeriodId: this.state.id,
            start: this.state.start,
            duration: this.state.duration,
            randomizeStart: this.state.randomizeStart
        };
        console.log('intervalPeriod => ' + JSON.stringify(intervalPeriod));
        console.log('id => ' + JSON.stringify(this.state.id));
        IntervalPeriodService.updateIntervalPeriod(intervalPeriod).then( res => {
            this.props.history.push('/intervalPeriods');
        });
    }

    changestartHandler= (event) => {
        this.setState({start: event.target.value});
    }
    changedurationHandler= (event) => {
        this.setState({duration: event.target.value});
    }
    changerandomizeStartHandler= (event) => {
        this.setState({randomizeStart: event.target.value});
    }

    cancel(){
        this.props.history.push('/intervalPeriods');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update IntervalPeriod</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> start: </label>
                                            #formFields( $attribute, 'update')
                                            <label> duration: </label>
                                            #formFields( $attribute, 'update')
                                            <label> randomizeStart: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateIntervalPeriod}>Save</button>
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

export default UpdateIntervalPeriodComponent
