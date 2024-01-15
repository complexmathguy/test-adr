import React, { Component } from 'react'
import IntervalService from '../services/IntervalService';

class UpdateIntervalComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
        }
        this.updateInterval = this.updateInterval.bind(this);

    }

    componentDidMount(){
        IntervalService.getIntervalById(this.state.id).then( (res) =>{
            let interval = res.data;
            this.setState({
            });
        });
    }

    updateInterval = (e) => {
        e.preventDefault();
        let interval = {
            intervalId: this.state.id,
        };
        console.log('interval => ' + JSON.stringify(interval));
        console.log('id => ' + JSON.stringify(this.state.id));
        IntervalService.updateInterval(interval).then( res => {
            this.props.history.push('/intervals');
        });
    }


    cancel(){
        this.props.history.push('/intervals');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Interval</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateInterval}>Save</button>
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

export default UpdateIntervalComponent
