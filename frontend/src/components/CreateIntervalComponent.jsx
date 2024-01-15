import React, { Component } from 'react'
import IntervalService from '../services/IntervalService';

class CreateIntervalComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
        }
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            IntervalService.getIntervalById(this.state.id).then( (res) =>{
                let interval = res.data;
                this.setState({
                });
            });
        }        
    }
    saveOrUpdateInterval = (e) => {
        e.preventDefault();
        let interval = {
                intervalId: this.state.id,
            };
        console.log('interval => ' + JSON.stringify(interval));

        // step 5
        if(this.state.id === '_add'){
            interval.intervalId=''
            IntervalService.createInterval(interval).then(res =>{
                this.props.history.push('/intervals');
            });
        }else{
            IntervalService.updateInterval(interval).then( res => {
                this.props.history.push('/intervals');
            });
        }
    }
    

    cancel(){
        this.props.history.push('/intervals');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Interval</h3>
        }else{
            return <h3 className="text-center">Update Interval</h3>
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
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateInterval}>Save</button>
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

export default CreateIntervalComponent
