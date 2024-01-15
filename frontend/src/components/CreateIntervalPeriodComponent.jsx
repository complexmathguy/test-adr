import React, { Component } from 'react'
import IntervalPeriodService from '../services/IntervalPeriodService';

class CreateIntervalPeriodComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                start: '',
                duration: '',
                randomizeStart: ''
        }
        this.changestartHandler = this.changestartHandler.bind(this);
        this.changedurationHandler = this.changedurationHandler.bind(this);
        this.changerandomizeStartHandler = this.changerandomizeStartHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            IntervalPeriodService.getIntervalPeriodById(this.state.id).then( (res) =>{
                let intervalPeriod = res.data;
                this.setState({
                    start: intervalPeriod.start,
                    duration: intervalPeriod.duration,
                    randomizeStart: intervalPeriod.randomizeStart
                });
            });
        }        
    }
    saveOrUpdateIntervalPeriod = (e) => {
        e.preventDefault();
        let intervalPeriod = {
                intervalPeriodId: this.state.id,
                start: this.state.start,
                duration: this.state.duration,
                randomizeStart: this.state.randomizeStart
            };
        console.log('intervalPeriod => ' + JSON.stringify(intervalPeriod));

        // step 5
        if(this.state.id === '_add'){
            intervalPeriod.intervalPeriodId=''
            IntervalPeriodService.createIntervalPeriod(intervalPeriod).then(res =>{
                this.props.history.push('/intervalPeriods');
            });
        }else{
            IntervalPeriodService.updateIntervalPeriod(intervalPeriod).then( res => {
                this.props.history.push('/intervalPeriods');
            });
        }
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

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add IntervalPeriod</h3>
        }else{
            return <h3 className="text-center">Update IntervalPeriod</h3>
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
                                            <label> start: </label>
                                            #formFields( $attribute, 'create')
                                            <label> duration: </label>
                                            #formFields( $attribute, 'create')
                                            <label> randomizeStart: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateIntervalPeriod}>Save</button>
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

export default CreateIntervalPeriodComponent
