import React, { Component } from 'react'
import IntervalService from '../services/IntervalService'

class ViewIntervalComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            interval: {}
        }
    }

    componentDidMount(){
        IntervalService.getIntervalById(this.state.id).then( res => {
            this.setState({interval: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Interval Details</h3>
                    <div className = "card-body">
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewIntervalComponent
